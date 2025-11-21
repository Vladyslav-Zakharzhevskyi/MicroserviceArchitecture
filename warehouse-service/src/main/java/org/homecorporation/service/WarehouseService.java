package org.homecorporation.service;

import java.util.List;
import java.util.Map;

public interface WarehouseService {
    Integer getProductAvailability(String ref);
    Map<String, Integer> getProductsAvailability(List<String> refs);
}
