package com.lzrc.ecommerce.services.product.insert.steps;

import com.lzrc.ecommerce.db.entities.Product;

public interface StepsBeforeInsert {

    void insert(Product product);
}
