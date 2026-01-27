package org.homecorporation.service;

import org.homecorporation.dto.ProductDTO;
import org.homecorporation.exception.ProductNotFoundException;
import org.homecorporation.feign.WarehouseClient;
import org.homecorporation.mapper.ProductMapper;
import org.homecorporation.model.Product;
import org.homecorporation.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.function.Predicate;

@Service
public class ProductsServiceImpl implements ProductsService {

    private final Logger logger = LoggerFactory.getLogger(ProductsServiceImpl.class);
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper mapper;
    @Autowired
    private WarehouseClient warehouseClient;
    @Autowired
    private ExecutorService fixedThreadPool;

    //@NewSpan(name = "getProduct", value = "val")
    @Override
    public ProductDTO getProduct(UUID id) {
        ProductDTO productInfo = productRepository.findById(id)
                .map(mapper::map)
                .orElseThrow(() -> new ProductNotFoundException(id));

        //get availability
        String warehouseRef = productInfo.getWarehouseRef();
        Integer availability = warehouseClient.getAvailability(warehouseRef);
        productInfo.setAvailableItemCount(availability);

        logger.info(String.format(String.format("Received from Warehouse service availability for Product ref: '%s'", warehouseRef)));

        return productInfo;
    }

    //@NewSpan(name = "getProducts", value = "val")
    @Override
    public List<ProductDTO> getProducts(Boolean onlyAvailable) {
        List<Product> products = productRepository.findAll();
        return this.extendWithAvailability(products, onlyAvailable);
    }

    private List<ProductDTO> extendWithAvailability(List<Product> products, Boolean onlyAvailable) {
        List<String> warehouseRefs = products.stream()
                .map(Product::getWarehouseRef)
                .toList();
        Map<String, Integer> availabilities = warehouseClient.getAvailabilities(warehouseRefs);

        logger.info(String.format("Received from Warehouse service: %s items.", availabilities.size()));

        //todo refactor onlyAvailable logic
        Predicate<ProductDTO> includeAvailableProducts = (product) -> availabilities.getOrDefault(product.getWarehouseRef(), 0) > 0;
        return products.stream()
                .map(mapper::map)
                .filter(pI -> !onlyAvailable || includeAvailableProducts.test(pI))
                .peek(pI -> pI.setAvailableItemCount(availabilities.getOrDefault(pI.getWarehouseRef(), 0)))
                .toList();
    }

    //@NewSpan(name = "getProductById", value = "val")
    @Override
    public List<ProductDTO> getProducts(List<UUID> ids, Boolean onlyAvailable) {
        List<Product> products = productRepository.findAllById(ids);
        return this.extendWithAvailability(products, onlyAvailable);
    }

}
