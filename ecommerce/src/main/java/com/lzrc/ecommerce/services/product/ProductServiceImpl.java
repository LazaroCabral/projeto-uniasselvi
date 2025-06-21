package com.lzrc.ecommerce.services.product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.db.repositories.ProductRepository;
import com.lzrc.ecommerce.services.product.exceptions.SaveImageException;

import jakarta.transaction.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

    @Value("${products.image.files.path}")
    String imagesPath;

    @Autowired
    ProductRepository productRepository;

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

}
