package com.lzrc.ecommerce.services.product.purchase.validators;

import com.lzrc.ecommerce.services.product.purchase.HeldProduct;

public interface HeldProductsValidator {

    boolean heldProductIsValid(HeldProduct heldProduct);

}
