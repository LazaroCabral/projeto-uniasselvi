package com.lzrc.ecommerce.services.product.update;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.db.repositories.custom.CustomProductRepository;
import com.lzrc.ecommerce.services.product.update.rules.ProductUpdateRules;
import com.lzrc.ecommerce.services.product.update.steps.ProductUpdateFlowStep;

@Service
public class ProductUpdateFlowImpl implements ProductUpdateFlow {

    @Autowired
    ProductUpdateFlowStep updateProductFlowStep;

    @Autowired
    CustomProductRepository customProductRepository;

    @Autowired
    ProductUpdateRules productUpdateRules;

    @Override
    public void update(Product product) {
        Optional<Product> optionalOriginalProduct = customProductRepository.findByIdWithWriteLock(product.getSku());
        if(optionalOriginalProduct.isPresent()){
            updateProductFlowStep.update(optionalOriginalProduct.get(), product);
        }
        
    }

}
