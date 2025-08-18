package com.lzrc.ecommerce.services.product.purchase;

import java.math.BigDecimal;

import com.lzrc.ecommerce.db.entities.Product;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HeldProduct {

    private Product product;
    private Long heldAt;

    public Long getHeldAt(){
        return this.heldAt;
    }

    public boolean skuIsEquals(String sku){
        return product.getSku().equals(sku);
    }

    public String getSku(){
        return this.product.getSku();
    }

    public String getName(){
        return product.getName();
    }

    public BigDecimal getPrice(){
        return product.getPrice();
    }

}
