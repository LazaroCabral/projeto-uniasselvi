package com.lzrc.ecommerce.services.product.purchase;

import java.math.BigDecimal;

import com.lzrc.ecommerce.db.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HeldProduct {

    Product product;
    Long heldAt;

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
