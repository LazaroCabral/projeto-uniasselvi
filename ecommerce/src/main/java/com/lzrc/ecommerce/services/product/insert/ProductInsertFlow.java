package com.lzrc.ecommerce.services.product.insert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.db.repositories.ProductRepository;
import com.lzrc.ecommerce.services.product.insert.steps.StepsBeforeInsert;

@Component
public class ProductInsertFlow {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    StepsBeforeInsert stepsBeforeInsert;

    @Transactional(propagation = Propagation.REQUIRED)
    public void insert(Product product){
        stepsBeforeInsert.insert(product);
        productRepository.save(product);
    }

}
