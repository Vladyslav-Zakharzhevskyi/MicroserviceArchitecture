package org.homecorporation.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public record ProductDTO(UUID id, String name,
                         String sku,  String warehouseRef,
                         Date createdAt, Date updatedAt,
                         BigDecimal price, Integer availableItemCount,
                         String description) {}
