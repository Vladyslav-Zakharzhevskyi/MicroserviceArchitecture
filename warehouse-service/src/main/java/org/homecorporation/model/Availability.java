package org.homecorporation.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Availability {
    @Id
    private String id;
    @Field
    @Indexed
    private String ref;
    @Field(value = "availableItemsCount")
    private Integer availabilityCount;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Integer getAvailabilityCount() {
        return availabilityCount;
    }

    public void setAvailabilityCount(Integer availabilityCount) {
        this.availabilityCount = availabilityCount;
    }
}
