package com.lzrc.ecommerce.services.product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.db.repositories.ProductRepository;
import com.lzrc.ecommerce.db.repositories.custom.CustomProductRepository;
import com.lzrc.ecommerce.services.product.exceptions.InsufficientStockException;
import com.lzrc.ecommerce.services.product.exceptions.ProductNotFoundException;
import com.lzrc.ecommerce.services.product.exceptions.SaveImageException;

@Service
public class ProductServiceImpl implements ProductService {

    @Value("${products.image.files.path}")
    String imagesPath;

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

    private boolean saveImage(byte[] productImage, String sku){
        String saveImage = imagesPath.concat("/").concat(sku).concat(".png");
        try {
            File file=new File(saveImage);
            file.createNewFile();
            FileOutputStream writer=new FileOutputStream(file);
            writer.write(productImage);
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public void saveWithImage(byte[] productImage, Product product) throws SaveImageException {
        boolean isSaved=saveImage(productImage, product.getSku());
        if(isSaved!=true){
            throw new SaveImageException();
        }
        productRepository.save(product);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void reduceStock(String sku, Long quantity) throws ProductNotFoundException, InsufficientStockException {
        Optional<Product> productOptional = productRepository.findById(sku);
        if(productOptional.isPresent()){
            Product product = productOptional.get();
            hasSufficientStock(product, quantity);
            customProductRepository.save(product);
        } else {throw new ProductNotFoundException();}
    }

}
