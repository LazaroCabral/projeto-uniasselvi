package com.lzrc.ecommerce.services.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lzrc.ecommerce.db.entities.Client;
import com.lzrc.ecommerce.db.repositories.ClientRepository;
import com.lzrc.ecommerce.services.client.exceptions.ClientAlreadyExistsException;
import com.lzrc.ecommerce.services.client.exceptions.ClientNotFoundException;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public void insert(Client client) throws ClientAlreadyExistsException {
        boolean clientExists=clientRepository.existsById(client.getCpf());
        if(clientExists == false){
            clientRepository.save(client);
        } else{
            throw new ClientAlreadyExistsException();
        }
    }

    @Override
    public void update(Client client) throws ClientNotFoundException {
        boolean clientExists = clientRepository.existsById(client.getCpf());
        if(clientExists){
            clientRepository.save(client);
        } else{
            throw new ClientNotFoundException();
        }
    }


}
