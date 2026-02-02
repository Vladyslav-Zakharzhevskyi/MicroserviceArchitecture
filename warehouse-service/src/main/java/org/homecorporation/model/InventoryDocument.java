package org.homecorporation.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(value = "availability")
public class InventoryDocument {
    @Id
    private String id;
    @Field
    @Indexed
    private String ref;
    @Field(value = "availableItemsCount")
    private Integer availableCount;
    @Field(value = "reservedCount")
    private Integer reservedCount;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Integer getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(Integer availableCount) {
        this.availableCount = availableCount;
    }
}
