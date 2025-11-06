package org.homecorporation.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class ProductInfoDto {
    private UUID id;
    private String name;
    private String sku;
    private String warehouseRef;
    private Date createdAt;
    private Date updatedAt;
    private BigDecimal price;
    private Integer availableItemCount;
    private String description;

    public ProductInfoDto(UUID id, String name, String sku, String warehouseRef,
                          Date createdAt, Date updatedAt, BigDecimal price,
                          Integer availableItemCount, String description) {
        this.id = id;
        this.name = name;
        this.sku = sku;
        this.warehouseRef = warehouseRef;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.price = price;
        this.availableItemCount = availableItemCount;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getWarehouseRef() {
        return warehouseRef;
    }

    public void setWarehouseRef(String warehouseRef) {
        this.warehouseRef = warehouseRef;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAvailableItemCount() {
        return availableItemCount;
    }

    public void setAvailableItemCount(Integer availableItemCount) {
        this.availableItemCount = availableItemCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
