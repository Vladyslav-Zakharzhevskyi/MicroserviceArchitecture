package org.homecorporation.controller;


import org.homecorporation.model.Client;
import org.homecorporation.repo.ClientsRepository;
import org.homecorporation.requestModel.ClientRequestModel;
import org.homecorporation.service.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/clients")
public class ClientsController {

    @Autowired
    private ClientsService clientsService;

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Mono<Client> getClient(@PathVariable(value = "id") Long id) {
        return clientsService.findClientById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Flux<Client> getClients() {
        return clientsService.findAllClients();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Mono<Client> createClient(@RequestBody ClientRequestModel requestModel) {
        return clientsService.createClient(requestModel);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Mono<Client> updateClient(@RequestBody ClientRequestModel requestModel) {
        return clientsService.updateClient(requestModel);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public Mono<Boolean> deleteClient(@PathVariable(value = "id") Long id) {
        return clientsService.deleteClientById(id);
    }



}
