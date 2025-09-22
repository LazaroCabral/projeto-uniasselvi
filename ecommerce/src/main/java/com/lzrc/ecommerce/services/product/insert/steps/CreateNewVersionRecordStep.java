package com.lzrc.ecommerce.services.product.insert.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.services.product.ProductUtils;

@Component
public class CreateNewVersionRecordStep implements StepsBeforeInsert {

    @Autowired
    ProductUtils productUtils;

    @Override
    public void insert(Product product) {
        productUtils.setNewProductVersion(product);
    }


}
