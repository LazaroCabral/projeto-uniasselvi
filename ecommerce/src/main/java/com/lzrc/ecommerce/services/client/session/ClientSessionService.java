package com.lzrc.ecommerce.services.client.session;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.services.client.exceptions.InsufficientBalanceException;
import com.lzrc.ecommerce.services.client.session.exceptions.ClientSessionIsInvalidException;
import com.lzrc.ecommerce.services.product.exceptions.ProductNotFoundException;
import com.lzrc.ecommerce.services.product.exceptions.ProductNotHeldException;

@Service
public interface ClientSessionService {

    void debit(BigDecimal value) throws InsufficientBalanceException, ClientSessionIsInvalidException;

    Product holdProduct(String sku) throws ProductNotFoundException;

    void buyProduct(String sku) throws InsufficientBalanceException, ProductNotHeldException, ClientSessionIsInvalidException;

}
