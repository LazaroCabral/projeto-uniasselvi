package com.lzrc.ecommerce.services.product.purchase.heldproducts.session;

import com.lzrc.ecommerce.services.product.purchase.HeldProduct;

public interface HeldProductsSessionStorage {

    void setHeldProductOnSession(HeldProduct heldProduct);

    HeldProduct getHeldProduct();

    void clearHeldProductFromSession();

}
