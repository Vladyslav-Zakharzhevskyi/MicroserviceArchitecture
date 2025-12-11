package org.homecorporation.service;

import org.homecorporation.exceptions.ProductAbsentInWarehouse;
import org.homecorporation.model.Availability;
import org.homecorporation.repo.AvailabilityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final Logger LOGGER = LoggerFactory.getLogger(WarehouseServiceImpl.class);
    public static final String AVAILABILITY_LOG_MSG = "Key:'%s'-Availability is:'%d' ";

    @Autowired
    private AvailabilityRepository repository;

    @Override
    public Integer getProductAvailability(String ref) {
        Availability availability = repository
                .findByRef(ref)
                .orElseThrow(() -> new ProductAbsentInWarehouse(ref));

        LOGGER.info(String.format(AVAILABILITY_LOG_MSG, ref, availability.getAvailabilityCount()));

        return availability.getAvailabilityCount();
    }

    @Override
    public Map<String, Integer> getProductsAvailability(List<String> refs) {
        Map<String, Integer> availability = repository.findByRefIn(refs)
                .stream()
                .collect(Collectors.toMap(Availability::getRef, Availability::getAvailabilityCount));

        LOGGER.info(availability
                .entrySet().stream()
                .reduce("Availability Info: ",
                        (val, entry) -> val.concat(String.format(AVAILABILITY_LOG_MSG, entry.getKey(), entry.getValue())),
                        String::concat));

        return availability;
    }
}
