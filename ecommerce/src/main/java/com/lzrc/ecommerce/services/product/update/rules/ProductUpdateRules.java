package com.lzrc.ecommerce.services.product.update.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.services.product.update.CompareProductCommonFields;
import com.lzrc.ecommerce.services.product.update.exceptions.ProductIsNotCachedToCompareException;

import jakarta.servlet.http.HttpSession;

@Service
public class ProductUpdateRules {

    @Autowired
    private HttpSession httpSession;

    private final String CACHED_PRODUCT = "productForUpdate";

    private Product getProduct(){
        Product product = (Product) httpSession.getAttribute(CACHED_PRODUCT);
        if(product != null){
            return product;
        } else {
            throw new ProductIsNotCachedToCompareException();
        }
    }

    public void setProductForUpdate(Product product){
        httpSession.setAttribute(CACHED_PRODUCT, product);
    }

    public boolean isTheSameProduct(Product product){
        return CompareProductCommonFields.productsFieldsIsEquals(product, getProduct());
    }

    public Long getAvailableStockDiference(Product product){
        Long availableStockDiference = product.getAvailableStock() - getProduct().getAvailableStock();
        return availableStockDiference;
    }

    public void clear(){
        httpSession.removeAttribute(CACHED_PRODUCT);
    }

    public boolean compareAndClear(Product product){
        boolean isEquals = isTheSameProduct(product);
        clear();
        return isEquals;
    }

}
