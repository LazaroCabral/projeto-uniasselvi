package com.lzrc.ecommerce.runners;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CreateProductsImagesDirectory implements CommandLineRunner {

    @Value("${products.image.files.path}")
    String path;

    @Override
    public void run(String... args) throws Exception {
        Path path = Paths.get(this.path);
        try{
            if(path.toFile().exists() == false)
                Files.createDirectories(path);
        } catch(IOException e ){
            System.out.println("Failed to create products images directory. Check the permissions on the especified directory!");
        }
        
    }
    
}
