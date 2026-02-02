package org.homecorporation.repo;

import org.homecorporation.model.InventoryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends MongoRepository<InventoryDocument, String> {
    Optional<InventoryDocument> findByRef(String ref);
    List<InventoryDocument> findByRefIn(List<String> refs);
}
