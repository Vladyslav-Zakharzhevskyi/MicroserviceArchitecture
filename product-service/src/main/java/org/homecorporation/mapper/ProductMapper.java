package org.homecorporation.mapper;

import org.homecorporation.dto.ProductInfoDto;
import org.homecorporation.model.ProductInfo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    ProductInfoDto doMapping(ProductInfo src);
    ProductInfo doMapping(ProductInfoDto src);
}
