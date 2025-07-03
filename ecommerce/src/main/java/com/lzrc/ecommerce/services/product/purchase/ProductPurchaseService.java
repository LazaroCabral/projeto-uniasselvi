package com.lzrc.ecommerce.services.product.purchase;

import org.springframework.stereotype.Service;

import com.lzrc.ecommerce.records.response.ProductRecordResponse;
import com.lzrc.ecommerce.services.client.exceptions.InsufficientBalanceException;
import com.lzrc.ecommerce.services.product.exceptions.InsufficientStockException;
import com.lzrc.ecommerce.services.product.exceptions.ProductNotFoundException;
import com.lzrc.ecommerce.services.product.exceptions.ProductNotHeldException;

@Service
public interface ProductPurchaseService {

    ProductRecordResponse holdProduct(String sku) throws ProductNotFoundException;

    void buyProduct(String sku) throws InsufficientBalanceException, ProductNotHeldException, ProductNotFoundException, InsufficientStockException;

}