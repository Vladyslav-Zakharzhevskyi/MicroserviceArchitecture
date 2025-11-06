package org.homecorporation.service;

import org.homecorporation.model.Availability;
import org.homecorporation.repo.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    private AvailabilityRepository repository;

    @Override
    public Integer getAvailableItemsForProduct(String ref) {
        return repository.findByRef(ref)
                .map(Availability::getAvailabilityCount)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Corresponding item is not present in db. Id '%s' not found.", ref)));
    }

    @Override
    public Map<String, Integer> getAvailableItemsForProduct(List<String> refs) {
        return repository.findByRefIn(refs).stream()
                .collect(Collectors.toMap(Availability::getRef, Availability::getAvailabilityCount));
    }
}
