package com.lzrc.ecommerce.services.product.update.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.db.repositories.ProductRepository;
import com.lzrc.ecommerce.db.repositories.custom.CustomProductRepository;
import com.lzrc.ecommerce.services.product.ProductUtils;
import com.lzrc.ecommerce.services.product.update.CompareProductCommonFields;

@Component
public class UpdateCommonProductFields implements ProductUpdateFlowStep {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomProductRepository customProductRepository;

    @Autowired
    ProductUtils productUtils;

    private boolean hasChanged(Product firstProduct, Product secondProduct){
        return !CompareProductCommonFields.productsFieldsIsEquals(firstProduct, secondProduct);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void update(Product originalProduct, Product updatedProduct) {
        if(hasChanged(originalProduct, updatedProduct)){
            productUtils.setNewProductVersion(updatedProduct);
            productRepository.save(updatedProduct);
        }
    }


}
