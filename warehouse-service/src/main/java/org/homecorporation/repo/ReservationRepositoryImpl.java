package org.homecorporation.repo;

import com.mongodb.client.result.UpdateResult;
import org.homecorporation.exceptions.NegativeReservedCountException;
import org.homecorporation.model.InventoryDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Long reserve(String productRef, Integer reservationCount) {
        Query query = new Query(
                Criteria.where("ref").is(productRef)
                        .and("availableCount").gte(reservationCount)
        );

        Update update = new Update()
                .inc("reservedCount", reservationCount)
                .inc("availableCount", -reservationCount);

        UpdateResult result =
                mongoTemplate.updateFirst(
                        query,
                        update,
                        InventoryDocument.class
                );

        return result.getModifiedCount();
    }

    @Override
    public void release(String productRef, Integer reservationCount) {
        Query query = Query.query(
                Criteria.where("ref").is(productRef)
                        .and("reservedCount").gte(reservationCount)
        );

        Update update = new Update()
                .inc("reservedCount", -reservationCount)
                .inc("availableCount", reservationCount);

        UpdateResult result = mongoTemplate.updateFirst(
                query,
                update,
                InventoryDocument.class
        );

        if (result.getModifiedCount() < 1) {
            throw new NegativeReservedCountException(productRef, reservationCount);
        }
    }
}
