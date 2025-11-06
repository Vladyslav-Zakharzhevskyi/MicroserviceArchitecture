package org.homecorporation.service;

import org.homecorporation.dto.ProductInfoDto;
import org.homecorporation.feign.WarehouseClient;
import org.homecorporation.mapper.ProductMapper;
import org.homecorporation.model.ProductInfo;
import org.homecorporation.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ProductsServiceImpl implements ProductsService {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private ProductMapper mapper;
    @Autowired
    private WarehouseClient warehouseClient;

    @Override
    public ProductInfoDto getProduct(UUID id) {
        ProductInfoDto productInfo = repository.findById(id)
                .map(info -> mapper.doMapping(info))
                .orElseThrow(() -> new IllegalArgumentException(String.format("Product with id '%s' was not found", id)));

        productInfo.setAvailableItemCount(warehouseClient.getAvailability(productInfo.getWarehouseRef()));

        return productInfo;
    }

    @Override
    public List<ProductInfoDto> getProducts(Boolean available) {
        List<ProductInfo> products = repository.findAll();
        return this.extendWithAvailability(products);
    }

    private List<ProductInfoDto> extendWithAvailability(List<ProductInfo> products) {
        Map<String, Integer> availability = warehouseClient.getAvailabilities(
                products.stream().map(ProductInfo::getWarehouseRef)
                        .toList());

        return products.stream()
                .map(info -> mapper.doMapping(info))
                .peek(dto -> dto.setAvailableItemCount(availability.getOrDefault(dto.getWarehouseRef(), 0)))
                .toList();
    }

    @Override
    public List<ProductInfoDto> getProducts(List<UUID> ids, Boolean available) {
        List<ProductInfo> products = repository.findAllById(ids);
        return this.extendWithAvailability(products);
    }
}
