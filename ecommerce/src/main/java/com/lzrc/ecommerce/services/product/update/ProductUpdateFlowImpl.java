package com.lzrc.ecommerce.services.product.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.services.product.update.rules.ProductUpdateRules;
import com.lzrc.ecommerce.services.product.update.steps.ProductUpdateFlowStep;

@Service
public class ProductUpdateFlowImpl implements ProductUpdateFlow {

    @Autowired
    ProductUpdateFlowStep updateProductFlowStep;

    @Autowired
    ProductUpdateRules productUpdateRules;

    @Override
    public void update(Product product) {
        updateProductFlowStep.update(product);
    }

    @Override
    public void setProductForUpdate(Product product) {
        productUpdateRules.setProductForUpdate(product);
    }


}
