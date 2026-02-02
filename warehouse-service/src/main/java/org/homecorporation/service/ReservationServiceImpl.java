package org.homecorporation.service;

import org.homecorporation.exceptions.ProductAbsentInWarehouse;
import org.homecorporation.model.InventoryDocument;
import org.homecorporation.repo.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationRepository repository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public Long makeReservation(String productRef, Integer reservationCount) {
        checkProductExistence(productRef);
        return repository.reserve(productRef, reservationCount);
    }

    @Override
    public void cancelReservation(String productRef, Integer reservationCount) {
        checkProductExistence(productRef);
        repository.release(productRef, reservationCount);
    }

    private void checkProductExistence(String productRef) {
        boolean exists = mongoTemplate.exists(Query.query(Criteria.where("ref").is(productRef)), InventoryDocument.class);
        if (!exists) {
            throw new ProductAbsentInWarehouse(productRef);
        }
    }

}
