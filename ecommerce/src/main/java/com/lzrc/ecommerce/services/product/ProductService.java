package com.lzrc.ecommerce.services.product;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.records.response.ProductRecordResponse;
import com.lzrc.ecommerce.services.product.exceptions.InsufficientStockException;
import com.lzrc.ecommerce.services.product.exceptions.ProductAlreadyExistsException;
import com.lzrc.ecommerce.services.product.exceptions.ProductNotFoundException;
import com.lzrc.ecommerce.services.product.image.exceptions.InvalidImageFormatException;
import com.lzrc.ecommerce.services.product.image.exceptions.SaveImageException;

@Service
public interface ProductService {

    Optional<ProductRecordResponse> findById(String sku);

    Page<ProductRecordResponse> findAllProducts(Pageable pageable);

    Page<ProductRecordResponse> searchProducts(String name, Pageable pageable);

    void saveProductImage(MultipartFile image, Product product) throws SaveImageException, InvalidImageFormatException;

    void insert(Product product) throws ProductAlreadyExistsException;

    void update(Product product) throws ProductNotFoundException;

    void delete(String sku);

    void reduceStock(String sku,Long quantity) throws ProductNotFoundException , InsufficientStockException;

}
