package com.lzrc.ecommerce.services.product;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.services.product.exceptions.SaveImageException;

public interface ProductService {

    void saveWithImage(byte[] productImage, Product product) throws SaveImageException;

    void save(Product product);

}
