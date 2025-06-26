package com.lzrc.ecommerce.services.product.purchase;

import org.springframework.stereotype.Service;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.services.client.exceptions.InsufficientBalanceException;
import com.lzrc.ecommerce.services.client.session.ClientSessionService;
import com.lzrc.ecommerce.services.client.session.exceptions.ClientSessionIsInvalidException;

@Service
public class ProductPurchaseServiceImpl implements ProductPurchaseService {

    @Override
    public void buyProduct(HeldProduct heldProduct, ClientSessionService clientSessionService) throws InsufficientBalanceException, ClientSessionIsInvalidException {
        Product product = heldProduct.getProduct();
        clientSessionService.debit(product.getPrice());
    }

}
