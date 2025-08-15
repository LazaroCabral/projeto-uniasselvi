package com.lzrc.ecommerce.services.product.purchase.validators;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lzrc.ecommerce.services.product.purchase.HeldProduct;

@Component
public class HeldProductTimeoutValidator implements HeldProductsValidator {

    @Value("${products.held-product-time-limit}")
    private Long heldProductTimeLimit;

    @Override
    public boolean heldProductIsValid(HeldProduct heldProduct) {
        Long heldProductTime = System.currentTimeMillis() - heldProduct.getHeldAt();
        if (heldProductTime > heldProductTimeLimit) {
            return false;
        }
        else {
            return true;
        }
    }

}
