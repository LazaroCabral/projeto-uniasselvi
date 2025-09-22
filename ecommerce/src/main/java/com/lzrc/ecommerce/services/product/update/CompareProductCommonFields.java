package com.lzrc.ecommerce.services.product.update;

import com.lzrc.ecommerce.db.entities.Product;

public class CompareProductCommonFields {

    private CompareProductCommonFields(){}

    public static boolean productsFieldsIsEquals(Product firstProduct, Product secondProduct) {

        if(generateHashCode(firstProduct) == generateHashCode(secondProduct)){
            if(firstProduct.getSku()
                .equals(secondProduct.getSku()) &&
                firstProduct.getName()
                    .equals(secondProduct.getName()) &&
                firstProduct.getPrice()
                    .equals(secondProduct.getPrice()) &&
                firstProduct.getDescription()
                    .equals(secondProduct.getDescription())){
                        return true;
            }
        }
        return false;
    }

    private static int generateHashCode(Product product) {
        int hash = 7;
        hash = 31 * hash + (product.getSku() == null? 0 : product.getSku().hashCode());
        hash = 31 * hash + (product.getName() == null? 0 : product.getName().hashCode());
        hash = 31 * hash + (product.getPrice() == null? 0 : product.getPrice().hashCode());
        hash = 31 * hash + (product.getDescription() == null? 0 : product.getDescription().hashCode());
        return hash;
    }

}
