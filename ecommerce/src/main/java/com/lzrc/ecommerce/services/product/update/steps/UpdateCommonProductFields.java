package com.lzrc.ecommerce.services.product.update.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.db.repositories.ProductRepository;
import com.lzrc.ecommerce.services.product.ProductUtils;
import com.lzrc.ecommerce.services.product.update.rules.ProductUpdateRules;

@Component
public class UpdateCommonProductFields implements ProductUpdateFlowStep {

    @Autowired
    ProductUpdateRules productUpdateRules;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductUtils productUtils;

    @Override
    public void update(Product product) {
        if(productUpdateRules.compareAndClear(product) == false){
            productUtils.setNewProductVersion(product);
            productRepository.save(product);
        };

    }


}
