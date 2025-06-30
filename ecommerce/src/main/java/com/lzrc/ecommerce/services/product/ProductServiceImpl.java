package com.lzrc.ecommerce.services.product;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.db.repositories.ProductRepository;
import com.lzrc.ecommerce.db.repositories.custom.CustomProductRepository;
import com.lzrc.ecommerce.services.product.exceptions.InsufficientStockException;
import com.lzrc.ecommerce.services.product.exceptions.ProductAlreadyExistsException;
import com.lzrc.ecommerce.services.product.exceptions.ProductNotFoundException;
import com.lzrc.ecommerce.services.product.image.ProductImageFileService;
import com.lzrc.ecommerce.services.product.image.exceptions.InvalidImageFormatException;
import com.lzrc.ecommerce.services.product.image.exceptions.SaveImageException;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductImageFileService productImageFileService;
    
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomProductRepository customProductRepository;

    private void hasSufficientStock(Product product, Long quantity) throws InsufficientStockException{
        Long remainingStock = product.getAvailableStock() - quantity;
        if(remainingStock >= 0){
            product.setAvailableStock(remainingStock);
        } else{throw new InsufficientStockException();}
    }

    @Override
    public void saveProductImage(MultipartFile image, Product product) throws SaveImageException, InvalidImageFormatException {
        productImageFileService.saveImage(image, product.getSku());
    }

    @Override
    public void insert(Product product) throws ProductAlreadyExistsException {
        boolean productAlreadyExists = productRepository.existsById(product.getSku());
        if(productAlreadyExists){
            throw new ProductAlreadyExistsException();
        } else {productRepository.save(product);}
    }

    @Override
    public void update(Product product) throws ProductNotFoundException {
        boolean productExists = productRepository.existsById(product.getSku());
        if(productExists){
            productRepository.save(product);
        } else {throw new ProductNotFoundException();}
    }

    @Override
    public void reduceStock(String sku, Long quantity) throws ProductNotFoundException, InsufficientStockException {
        Optional<Product> productOptional = customProductRepository.findByIdWithWriteLock(sku);
        if(productOptional.isPresent()){
            Product product = productOptional.get();
            hasSufficientStock(product, quantity);
            customProductRepository.save(product);
        } else {throw new ProductNotFoundException();}
    }

}
