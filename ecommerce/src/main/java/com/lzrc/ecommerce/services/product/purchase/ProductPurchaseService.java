package com.lzrc.ecommerce.services.product.purchase;

import org.springframework.stereotype.Service;

import com.lzrc.ecommerce.services.client.exceptions.InsufficientBalanceException;
import com.lzrc.ecommerce.services.client.session.ClientSessionService;
import com.lzrc.ecommerce.services.client.session.exceptions.ClientSessionIsInvalidException;

@Service
public interface ProductPurchaseService {

    void buyProduct(HeldProduct heldProduct, ClientSessionService clientSessionService) throws InsufficientBalanceException, ClientSessionIsInvalidException;

}