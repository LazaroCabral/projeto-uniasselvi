package com.lzrc.ecommerce.services.product.update.bean.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.services.product.update.steps.ProductUpdateFlowStep;

@Component
public class UpdateProductFlowBean {

    @Bean
    @Primary
    public ProductUpdateFlowStep updateProductFlowStep(List<ProductUpdateFlowStep> updateProductFlowSteps){
        return new UpdateProductFlowBuilder(updateProductFlowSteps).build();
    }

    class UpdateProductFlowBuilder{

        private List<ProductUpdateFlowStep> updateProductFlowSteps;

        UpdateProductFlowBuilder(List<ProductUpdateFlowStep> updateProductFlowSteps){
            this.updateProductFlowSteps = updateProductFlowSteps;
        }
        
        public ProductUpdateFlowStep build(){
            return new ProductUpdateFlowStep() {
                @Override
                public void update(Product product) {
                    updateProductFlowSteps.
                        forEach( updateProductFlow -> {
                            updateProductFlow.update(product);
                        } );
                }
            };
            
        }

    }
}
