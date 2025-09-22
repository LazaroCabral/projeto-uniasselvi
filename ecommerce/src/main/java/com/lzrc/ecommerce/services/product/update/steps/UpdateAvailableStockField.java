package com.lzrc.ecommerce.services.product.update.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.db.repositories.custom.CustomProductRepository;
import com.lzrc.ecommerce.services.product.update.rules.ProductUpdateRules;

@Component
public class UpdateAvailableStockField implements ProductUpdateFlowStep {

    @Autowired
    CustomProductRepository customProductRepository;

    @Autowired
    ProductUpdateRules productUpdateRules;

    @Override
    public void update(Product product) {
        Long plusStock = getProcessedAvailableStock(product);
        if(plusStock > 0){
            customProductRepository.incrementAvailableStock(
                product.getSku(), plusStock);
        }
    }

    private Long getProcessedAvailableStock(Product product){
        Long stockToChange = productUpdateRules.getAvailableStockDiference(product);
        if(stockToChange <= 0L){
            return 0L;
        }
        return stockToChange ;

    }

}
