package com.lzrc.ecommerce.services.product.purchase;

import java.math.BigDecimal;

import com.lzrc.ecommerce.db.entities.ProductVersion;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HeldProduct {

    private ProductVersion productVersion;
    private Long heldAt;

    public Long getHeldAt(){
        return this.heldAt;
    }

    public ProductVersion getProductVersion(){
        return this.productVersion;
    }

    public boolean skuIsEquals(String sku){
        return productVersion.getSku().equals(sku);
    }

    public String getSku(){
        return this.productVersion.getSku();
    }

    public String getName(){
        return productVersion.getName();
    }

    public BigDecimal getPrice(){
        return productVersion.getPrice();
    }

}
