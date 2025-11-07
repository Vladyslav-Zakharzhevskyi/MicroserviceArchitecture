package org.homecorporation.service;

import org.homecorporation.dto.ProductInfoDto;

import java.util.List;
import java.util.UUID;

public interface ProductsService {
    ProductInfoDto getProduct(UUID id);
    List<ProductInfoDto> getProducts(Boolean onlyAvailable);
    List<ProductInfoDto> getProducts(List<UUID> ids, Boolean onlyAvailable);
}
