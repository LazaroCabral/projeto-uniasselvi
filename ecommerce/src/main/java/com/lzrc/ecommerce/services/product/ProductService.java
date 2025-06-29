package com.lzrc.ecommerce.services.product;

import org.springframework.web.multipart.MultipartFile;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.services.product.exceptions.InsufficientStockException;
import com.lzrc.ecommerce.services.product.exceptions.InvalidImageFormatException;
import com.lzrc.ecommerce.services.product.exceptions.ProductNotFoundException;
import com.lzrc.ecommerce.services.product.exceptions.SaveImageException;

public interface ProductService {

    void saveProductImage(MultipartFile image, Product product) throws SaveImageException, InvalidImageFormatException;

    void save(Product product);

    void reduceStock(String sku,Long quantity) throws ProductNotFoundException , InsufficientStockException;

}
