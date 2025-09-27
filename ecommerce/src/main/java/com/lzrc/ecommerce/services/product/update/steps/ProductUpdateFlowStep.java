package com.lzrc.ecommerce.services.product.update.steps;

import com.lzrc.ecommerce.db.entities.Product;

public interface ProductUpdateFlowStep {

    void update(Product originalProduct, Product updatedProduct);
    
}
