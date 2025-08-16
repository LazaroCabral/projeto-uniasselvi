package com.lzrc.ecommerce.services.product.purchase.heldproducts.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lzrc.ecommerce.services.product.purchase.HeldProduct;

import jakarta.servlet.http.HttpSession;

@Component
public class HeldProductsSessionStorageImpl implements HeldProductsSessionStorage {

    @Autowired
    HttpSession session;

    private final String HELD_PRODUCT = "heldproduct";

    public void setHeldProductOnSession(HeldProduct heldProduct){
        session.setAttribute(HELD_PRODUCT, heldProduct);
    }

    public HeldProduct getHeldProduct(){
        return (HeldProduct) session.getAttribute(HELD_PRODUCT);
    }

    public void clearHeldProductFromSession(){
        session.removeAttribute(HELD_PRODUCT);
    }

}
