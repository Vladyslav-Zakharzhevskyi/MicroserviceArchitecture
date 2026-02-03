package org.homecorporation.mapper;

import org.homecorporation.dto.ProductDTO;
import org.homecorporation.feign.WarehouseClient;
import org.homecorporation.model.Product;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    @Mapping(target = "availableItemCount", source = "warehouseRef", qualifiedByName = "availability")
    ProductDTO map(Product src, @Context WarehouseClient warehouseClient, @Context Boolean showAvailability);
    @Mapping(target = "availableItemCount", source = "warehouseRef", qualifiedByName = "availabilityMulti")
    ProductDTO map(Product src, @Context Boolean showAvailability, @Context Map<String, Integer> availabilities);
    List<ProductDTO> map(List<Product> src, @Context Boolean showAvailability, @Context Map<String, Integer> availabilities);
    Product map(ProductDTO src);

    @Named("availability")
    default Integer getAvailability(String warehouseRef, @Context WarehouseClient client, @Context Boolean showAvailability) {
        if (!showAvailability)
            return null;

        return client.getAvailability(warehouseRef);
    }

    @Named("availabilityMulti")
    default Integer availability(String warehouseRef, @Context Boolean showAvailability, @Context Map<String, Integer> availabilities) {

        if (!showAvailability)
            return null;

        return availabilities.getOrDefault(warehouseRef, 0);
    }

}
