package com.lzrc.ecommerce.services.product.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lzrc.ecommerce.services.product.exceptions.InvalidImageFormatException;
import com.lzrc.ecommerce.services.product.exceptions.SaveImageException;

@Service
public class ProductImageFileService {

    @Value("${products.image.files.path}")
    String imagesPath;

    private void imageIsValid(MultipartFile image) throws InvalidImageFormatException {
        String productImageName = image.getOriginalFilename();
        if(productImageName == null || 
            productImageName.matches("^.*?(\\.png)") == false){
            throw new InvalidImageFormatException();
        }
    }

    public boolean saveImage(MultipartFile image, String sku) throws InvalidImageFormatException, SaveImageException {
        imageIsValid(image);
        String saveImage = imagesPath.concat("/").concat(sku).concat(".png");
        try {
            byte[] productImage = image.getBytes();
            File file=new File(saveImage);
            file.createNewFile();
            FileOutputStream writer=new FileOutputStream(file);
            writer.write(productImage);
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            throw new SaveImageException();
        }
    }
    
}
