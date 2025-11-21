package org.homecorporation.service;

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

    private Logger logger = LoggerFactory.getLogger(WarehouseServiceImpl.class);

    @Autowired
    private AvailabilityRepository repository;

    @Override
    public Integer getProductAvailability(String ref) {
        Integer availability = repository.findByRef(ref)
                .map(Availability::getAvailabilityCount)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Corresponding item is not present in db. Id '%s' not found.", ref)));

        logger.info(String.format("Retrieved Availability for Ref: '%s' with value: '%s'", ref, availability));

        return availability;
    }

    @Override
    public Map<String, Integer> getProductsAvailability(List<String> refs) {
        Map<String, Integer> availabilityMap = repository.findByRefIn(refs).stream()
                .collect(Collectors.toMap(Availability::getRef, Availability::getAvailabilityCount));

        String info = availabilityMap.keySet()
                .stream().reduce("Availability: ", (seq, key) -> seq + String.format("Key:'%s'-Items:'%d' ", key, availabilityMap.get(key)));

        logger.info(String.format("Retrieved Availabilities '%s' ", info));

        return availabilityMap;
    }
}
