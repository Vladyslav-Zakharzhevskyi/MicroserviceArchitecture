package org.homecorporation.service;

import org.homecorporation.exceptions.ProductAbsentInWarehouse;
import org.homecorporation.model.InventoryDocument;
import org.homecorporation.repo.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final Logger LOGGER = LoggerFactory.getLogger(InventoryServiceImpl.class);
    public static final String LOG_MSG = "Key:'%s'-Availability is:'%d' ";

    @Autowired
    private InventoryRepository repository;

    @Override
    public Integer getInventory(String productRef) {
        InventoryDocument inventoryDocument = repository
                .findByRef(productRef)
                .orElseThrow(() -> new ProductAbsentInWarehouse(productRef));

        LOGGER.info(String.format(LOG_MSG, productRef, inventoryDocument.getAvailableCount()));

        return inventoryDocument.getAvailableCount();
    }

    @Override
    public Map<String, Integer> getInventory(List<String> productRefs) {
        Map<String, Integer> availability = repository.findByRefIn(productRefs)
                .stream()
                .collect(Collectors.toMap(InventoryDocument::getRef, InventoryDocument::getAvailableCount));

        LOGGER.info(availability
                .entrySet().stream()
                .reduce("Availability Info: ",
                        (val, entry) -> val.concat(String.format(LOG_MSG, entry.getKey(), entry.getValue())),
                        String::concat));

        return availability;
    }
}
