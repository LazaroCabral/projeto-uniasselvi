package com.lzrc.ecommerce.services.client.session;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.lzrc.ecommerce.services.client.exceptions.InsufficientBalanceException;

@Service
public interface ClientSessionService {

    void debit(BigDecimal value) throws InsufficientBalanceException;

}
