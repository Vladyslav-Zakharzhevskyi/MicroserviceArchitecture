package org.homecorporation.mapper;

import org.homecorporation.dto.ProductDTO;
import org.homecorporation.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    ProductDTO map(Product src);
    Product map(ProductDTO src);
}
