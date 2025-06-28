package com.lzrc.ecommerce.services.client.session;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.lzrc.ecommerce.services.client.ClientService;
import com.lzrc.ecommerce.services.client.exceptions.ClientNotFoundException;
import com.lzrc.ecommerce.services.client.exceptions.InsufficientBalanceException;
import com.lzrc.ecommerce.services.client.session.exceptions.ClientSessionIsInvalidException;

import jakarta.servlet.http.HttpSession;

@Service
public class ClientSessionServiceImpl implements ClientSessionService{

    @Autowired
    ClientService clientService;

    @Autowired
    HttpSession session;

    private void invalidateSession(){
        session.invalidate();
    }

    @Override
    public void debit(BigDecimal value) throws InsufficientBalanceException, ClientSessionIsInvalidException {
        String cpf = SecurityContextHolder
            .getContext().getAuthentication().getName();
        try {
            clientService.debit(cpf, value);
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
            invalidateSession();
            throw new ClientSessionIsInvalidException();
        }
    }

   
}
