package com.lzrc.ecommerce.services.product.update.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.db.repositories.custom.CustomProductRepository;

@Component
public class UpdateAvailableStockField implements ProductUpdateFlowStep {

    @Autowired
    CustomProductRepository customProductRepository;

    @Override
    public void update(Product originalProduct, Product updatedProduct) {
        if(updatedProduct.getAvailableStock() > 0){
            customProductRepository.incrementAvailableStock(
                originalProduct.getSku(), updatedProduct.getAvailableStock());
        }
    }

}
