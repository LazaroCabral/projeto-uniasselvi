package com.lzrc.ecommerce.services.client;

import org.springframework.stereotype.Service;

import com.lzrc.ecommerce.db.entities.Client;
import com.lzrc.ecommerce.services.client.exceptions.ClientAlreadyExistsException;
import com.lzrc.ecommerce.services.client.exceptions.ClientNotFoundException;

@Service
public interface ClientService {

    void insert(Client client) throws ClientAlreadyExistsException;

    void update(Client client) throws ClientNotFoundException;

}
