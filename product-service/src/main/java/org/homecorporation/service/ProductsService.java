package org.homecorporation.service;

import org.homecorporation.dto.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface ProductsService {
    ProductDTO getProduct(UUID id, Boolean showAvailability);
    List<ProductDTO> getProducts(Boolean onlyAvailable, Boolean showAvailability);
    List<ProductDTO> getProducts(List<UUID> ids, Boolean onlyAvailable);
}
