package org.homecorporation.service;

import org.homecorporation.dto.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface ProductsService {
    ProductDTO getProduct(UUID id);
    List<ProductDTO> getProducts(Boolean onlyAvailable);
    List<ProductDTO> getProducts(List<UUID> ids, Boolean onlyAvailable);
}
