package com.lzrc.ecommerce.services.client;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.lzrc.ecommerce.db.entities.Client;
import com.lzrc.ecommerce.services.client.exceptions.ClientAlreadyExistsException;
import com.lzrc.ecommerce.services.client.exceptions.ClientNotFoundException;
import com.lzrc.ecommerce.services.client.exceptions.InsufficientBalanceException;

@Service
public interface ClientService {

    void insert(Client client) throws ClientAlreadyExistsException;

    void update(Client client) throws ClientNotFoundException;

    void debit(String cpf, BigDecimal value) throws InsufficientBalanceException, ClientNotFoundException;

}
