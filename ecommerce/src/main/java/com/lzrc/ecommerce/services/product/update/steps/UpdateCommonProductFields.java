package com.lzrc.ecommerce.services.product.update.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.db.repositories.ProductRepository;
import com.lzrc.ecommerce.db.repositories.custom.CustomProductRepository;
import com.lzrc.ecommerce.services.product.ProductUtils;
import com.lzrc.ecommerce.services.product.update.rules.ProductUpdateRules;

@Component
public class UpdateCommonProductFields implements ProductUpdateFlowStep {

    @Autowired
    ProductUpdateRules productUpdateRules;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomProductRepository customProductRepository;

    @Autowired
    ProductUtils productUtils;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void update(Product product) {
        int isUpdated = customProductRepository.updateCommonFields(
            product.getSku(), product.getName(), 
            product.getDescription(), product.getPrice());
        if(isUpdated == 1){
            productUtils.setNewProductVersion(product);
            productRepository.save(product);
        }

    }


}
