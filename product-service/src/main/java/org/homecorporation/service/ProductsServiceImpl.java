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

@Service
public class ProductsServiceImpl implements ProductsService {

    private final Logger logger = LoggerFactory.getLogger(ProductsServiceImpl.class);
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper mapper;
    @Autowired
    private WarehouseClient warehouseClient;

    //@NewSpan(name = "getProduct", value = "val")
    @Override
    public ProductDTO getProduct(UUID id, Boolean showAvailability) {
        ProductDTO productInfo = productRepository.findById(id)
                .map(product -> mapper.map(product, warehouseClient, showAvailability))
                .orElseThrow(() -> new ProductNotFoundException(id));

        return productInfo;
    }

    //@NewSpan(name = "getProducts", value = "val")
    @Override
    public List<ProductDTO> getProducts(Boolean onlyAvailable, Boolean showAvailability) {
        List<Product> products = productRepository.findAll();
        return getProductsWithAvailability(products, showAvailability);
    }

    private List<ProductDTO> getProductsWithAvailability(List<Product> products, Boolean showAvailability) {
        List<String> warehouseRefs = products.stream()
                .map(Product::getWarehouseRef)
                .toList();

        Map<String, Integer> availabilities = warehouseClient.getAvailabilities(warehouseRefs);

        return mapper.map(products, showAvailability, availabilities);
    }


    //@NewSpan(name = "getProductById", value = "val")
    @Override
    public List<ProductDTO> getProducts(List<UUID> ids, Boolean showAvailability) {
        List<Product> products = productRepository.findAllById(ids);
        return getProductsWithAvailability(products, showAvailability);
    }

}
