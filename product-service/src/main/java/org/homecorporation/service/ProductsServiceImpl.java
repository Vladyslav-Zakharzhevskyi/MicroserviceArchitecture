package org.homecorporation.service;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.annotation.NewSpan;
import org.homecorporation.dto.ProductInfoDto;
import org.homecorporation.feign.WarehouseClient;
import org.homecorporation.mapper.ProductMapper;
import org.homecorporation.model.ProductInfo;
import org.homecorporation.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

@Service
public class ProductsServiceImpl implements ProductsService {

    private final Logger logger = LoggerFactory.getLogger(ProductsServiceImpl.class);
    @Autowired
    private ObservationRegistry observationRegistry;
    @Autowired
    private Tracer tracer;
    @Autowired
    private ProductRepository repository;
    @Autowired
    private ProductMapper mapper;
    @Autowired
    private WarehouseClient warehouseClient;

//    @NewSpan(name = "getProduct", value = "val")
    @Override
    public ProductInfoDto getProduct(UUID id) {
//        observationRegistry.getCurrentObservation().
        ProductInfoDto productInfo = repository.findById(id)
                .map(info -> mapper.doMapping(info))
                .orElseThrow(() -> new IllegalArgumentException(String.format("Product with id '%s' was not found", id)));

        productInfo.setAvailableItemCount(warehouseClient.getAvailability(productInfo.getWarehouseRef()));

        logger.info(String.format(String.format("Received from Warehouse service availability for Product ref: '%s'", productInfo.getWarehouseRef())));

        return productInfo;
    }

//    @NewSpan(name = "getProducts", value = "val")
    @Override
    public List<ProductInfoDto> getProducts(Boolean onlyAvailable) {
        List<ProductInfo> products = repository.findAll();
        return this.extendWithAvailability(products, onlyAvailable);
    }

    private List<ProductInfoDto> extendWithAvailability(List<ProductInfo> products, Boolean onlyAvailable) {
        Map<String, Integer> availability = warehouseClient.getAvailabilities(
                products.stream().map(ProductInfo::getWarehouseRef)
                        .toList());

        logger.info(String.format("Received from Warehouse service: %s items.", availability.size()));

        Predicate<ProductInfoDto> includeAvailableProducts = (product) -> availability.getOrDefault(product.getWarehouseRef(), 0) > 0;

        return products.stream()
                .map(info -> mapper.doMapping(info))
                .filter(product -> onlyAvailable ? includeAvailableProducts.test(product) : true)
                .peek(dto -> dto.setAvailableItemCount(availability.getOrDefault(dto.getWarehouseRef(), 0)))
                .toList();
    }

//    @NewSpan(name = "getProductById", value = "val")
    @Override
    public List<ProductInfoDto> getProducts(List<UUID> ids, Boolean onlyAvailable) {
        List<ProductInfo> products = repository.findAllById(ids);
        return this.extendWithAvailability(products, onlyAvailable);
    }
}
