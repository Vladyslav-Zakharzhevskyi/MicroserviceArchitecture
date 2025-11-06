package org.homecorporation.repo;

import org.homecorporation.model.Availability;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvailabilityRepository extends MongoRepository<Availability, String> {
    Optional<Availability> findByRef(String ref);
    List<Availability> findByRefIn(List<String> refs);
}
