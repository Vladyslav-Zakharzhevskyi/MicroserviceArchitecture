package org.homecorporation.service;

import java.util.List;
import java.util.Map;

public interface WarehouseService {
    Integer getAvailableItemsForProduct(String ref);
    Map<String, Integer> getAvailableItemsForProduct(List<String> refs);
}
