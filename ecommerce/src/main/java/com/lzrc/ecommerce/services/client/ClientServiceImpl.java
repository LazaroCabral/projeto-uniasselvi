package com.lzrc.ecommerce.services.client;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lzrc.ecommerce.db.entities.Client;
import com.lzrc.ecommerce.db.repositories.ClientRepository;
import com.lzrc.ecommerce.db.repositories.custom.CustomClientRepository;
import com.lzrc.ecommerce.records.ClientRecord;
import com.lzrc.ecommerce.services.client.exceptions.ClientAlreadyExistsException;
import com.lzrc.ecommerce.services.client.exceptions.ClientNotFoundException;
import com.lzrc.ecommerce.services.client.exceptions.InsufficientBalanceException;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CustomClientRepository customClientRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private void encodeClientPassword(Client client){
        client.setPassword(
            passwordEncoder.encode(client.getPassword())
        );
    }

    private void debit(Client client, BigDecimal value) throws InsufficientBalanceException{
        if(client.getAccountBalance().compareTo(value) >= 0){
            client.setAccountBalance(
            client.getAccountBalance().subtract(value));
            customClientRepository.save(client);
        } else {throw new InsufficientBalanceException();}
    }

    @Override
    public void insert(ClientRecord clientRecord) throws ClientAlreadyExistsException {
        boolean clientExists=clientRepository.existsById(clientRecord.cpf());
        if(clientExists == false){
            Client client = new Client( clientRecord.cpf(),
                clientRecord.name(),
                 clientRecord.email(),
                clientRecord.password(),
                BigDecimal.ZERO);
            encodeClientPassword(client);
            clientRepository.save(client);
        } else{
            throw new ClientAlreadyExistsException();
        }
    }

    @Override
    public void update(Client client) throws ClientNotFoundException {
        boolean clientExists = clientRepository.existsById(client.getCpf());
        if(clientExists){
            encodeClientPassword(client);
            clientRepository.save(client);
        } else{
            throw new ClientNotFoundException();
        }
    }

    @Override
    public void debit(String cpf, BigDecimal value) throws InsufficientBalanceException, ClientNotFoundException {
        Optional<Client> optionalClient = 
            customClientRepository.findByIdWithWriteLock(cpf);
        if(optionalClient.isPresent()){
            Client client = optionalClient.get();
            debit(client, value);
        } else {
            throw new ClientNotFoundException();}
    }
    

}
