package com.lzrc.ecommerce.services.product;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.db.entities.ProductVersion;
import com.lzrc.ecommerce.db.repositories.ProductVersionsRepository;

@Component
public class ProductUtils {

    @Autowired
    ProductVersionsRepository productVersionsRepository;

    private ProductVersion createProductVersion(Product product){
        return new ProductVersion(
            product.getSku(), product.getName(), product.getPrice(),
            product.getDescription(),LocalDateTime.now());
    }

    public void setNewProductVersion(Product product){
        ProductVersion productVersion = createProductVersion(product);
        productVersion = productVersionsRepository.save(productVersion);
        product.setProductVersion(productVersion);
    }
    
}
