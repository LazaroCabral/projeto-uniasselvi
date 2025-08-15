package com.lzrc.ecommerce.services.product.purchase.validators.config;

import java.util.Iterator;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.lzrc.ecommerce.services.product.purchase.HeldProduct;
import com.lzrc.ecommerce.services.product.purchase.validators.HeldProductTimeoutValidator;
import com.lzrc.ecommerce.services.product.purchase.validators.HeldProductsValidator;

@Component
public class HeldProductsValidatorBeanConfig {

    @Autowired
    HeldProductTimeoutValidator heldProductTimeoutValidator;

    @Bean
    public HeldProductsValidator productsValidator(){
        return new Builder()
            .addValidator(heldProductTimeoutValidator)
            .build();
    }

    class Builder{

        LinkedList<HeldProductsValidator> productValidators = new LinkedList<>();

        public Builder addValidator(HeldProductsValidator productsValidator){
            this.productValidators.add(productsValidator);
            return this;
        }

        public HeldProductsValidator build(){
            return new HeldProductsValidator() {

                @Override
                public boolean heldProductIsValid(HeldProduct heldProduct) {
                    Iterator<HeldProductsValidator> validators = productValidators.iterator();
                    boolean productIsValid = true;
                    while (validators.hasNext()) {
                        productIsValid = validators.next().heldProductIsValid(heldProduct);
                        if(productIsValid){
                            continue;
                        } else{
                            break;
                        }
                    }
                    return productIsValid;
                }
                
            };
        }

    }

}
