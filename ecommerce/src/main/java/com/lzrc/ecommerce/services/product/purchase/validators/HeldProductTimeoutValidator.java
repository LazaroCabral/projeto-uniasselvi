package com.lzrc.ecommerce.services.product.purchase.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lzrc.ecommerce.services.application.management.ApplicationManagementService;
import com.lzrc.ecommerce.services.product.purchase.HeldProduct;

@Component
public class HeldProductTimeoutValidator implements HeldProductsValidator {

    @Autowired
    private ApplicationManagementService applicationManagementService;

    @Override
    public boolean heldProductIsValid(HeldProduct heldProduct) {
        Long heldProductTime = System.currentTimeMillis() - heldProduct.getHeldAt();
        if (heldProductTime > applicationManagementService.getHeldProductsTimeout()) {
            return false;
        }
        else {
            return true;
        }
    }

}
