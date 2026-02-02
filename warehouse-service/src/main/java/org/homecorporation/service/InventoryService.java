package org.homecorporation.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface InventoryService {
    Integer getInventory(String productRef);
    Map<String, Integer> getInventory(List<String> productRefs);
}
