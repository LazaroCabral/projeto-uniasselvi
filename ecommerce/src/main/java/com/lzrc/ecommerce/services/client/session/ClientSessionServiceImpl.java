package com.lzrc.ecommerce.services.client.session;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.lzrc.ecommerce.db.entities.Client;
import com.lzrc.ecommerce.services.client.ClientService;
import com.lzrc.ecommerce.services.client.exceptions.ClientNotFoundException;
import com.lzrc.ecommerce.services.client.exceptions.InsufficientBalanceException;
import com.lzrc.ecommerce.services.client.session.exceptions.ClientSessionIsInvalidException;

@Service
public class ClientSessionServiceImpl implements ClientSessionService{

    @Autowired
    ClientService clientService;

    @Override
    public void debit(BigDecimal value) throws InsufficientBalanceException {
        String cpf = SecurityContextHolder
            .getContext().getAuthentication().getName();
        try {
            clientService.debit(cpf, value);
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
            throw new ClientSessionIsInvalidException();
        }
    }

    @Override
    public Client getActiveClient(){
        String cpf = SecurityContextHolder.getContext()
            .getAuthentication().getName();
        Optional<Client> optionalClient = clientService.findById(cpf);
        if(optionalClient.isPresent()){
            return optionalClient.get();
        } else{ throw new ClientSessionIsInvalidException();}
    }

   
}
