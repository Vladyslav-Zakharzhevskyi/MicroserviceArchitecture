package org.homecorporation.service;

import org.homecorporation.model.Client;
import org.homecorporation.repo.ClientsRepository;
import org.homecorporation.requestModel.ClientRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ClientsServiceImpl implements ClientsService {
    @Autowired
    private ClientsRepository clientsRepository;

    @Override
    public Mono<Client> createClient(ClientRequestModel model) {
        Client client = new Client(null, model.email());
        return clientsRepository.save(client);
    }

    @Override
    public Mono<Client> updateClient(ClientRequestModel model) {
        return clientsRepository
                .findById(model.id())
                .doOnSuccess(client -> clientsRepository.save(new Client(client.id(), client.email())));
    }

    @Override
    public Flux<Client> findAllClients() {
        return clientsRepository.findAll();
    }

    @Override
    public Mono<Client> findClientById(Long id) {
        return clientsRepository
                .findById(id);
    }

    @Override
    public Mono<Boolean> deleteClientById(Long id) {
        return clientsRepository
                .deleteById(id)
                .map(x -> true);
    }

    @Override
    public Mono<Boolean> deleteAllClientsByIds(List<Long> ids) {
        return clientsRepository.deleteAllById(ids)
                .map(unused -> true);
    }
}
