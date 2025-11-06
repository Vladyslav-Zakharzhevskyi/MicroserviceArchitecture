package org.homecorporation.repo;

import org.homecorporation.model.Client;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ClientsRepository extends ReactiveCrudRepository<Client, Long> {
}
