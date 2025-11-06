package org.homecorporation.service;

import org.homecorporation.model.Client;
import org.homecorporation.requestModel.ClientRequestModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ClientsService {
    Mono<Client> createClient(ClientRequestModel model);
    Mono<Client> updateClient(ClientRequestModel model);
    Flux<Client> findAllClients();
    Mono<Client> findClientById(Long id);
    Mono<Boolean> deleteClientById(Long id);
    Mono<Boolean> deleteAllClientsByIds(List<Long> ids);
}
