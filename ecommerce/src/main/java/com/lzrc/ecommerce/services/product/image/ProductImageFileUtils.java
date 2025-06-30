package com.lzrc.ecommerce.services.product.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.lzrc.ecommerce.services.product.image.exceptions.InvalidImageFormatException;
import com.lzrc.ecommerce.services.product.image.exceptions.SaveImageException;

public class ProductImageFileUtils {

    public static void imageIsValid(MultipartFile image) throws InvalidImageFormatException {
        String productImageName = image.getOriginalFilename();
        if(productImageName == null || 
            productImageName.matches("^.*?(\\.png)") == false){
            throw new InvalidImageFormatException();
        }
    }

    public static void writeImage(byte[] productImage,File file) throws SaveImageException{
        try {
            FileOutputStream writer=new FileOutputStream(file);
            writer.write(productImage);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new SaveImageException();
        }

    }

    public static void createImage(byte[] image, File imageFile) throws SaveImageException{
        try {
            imageFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            throw new SaveImageException();
        }
        writeImage(image, imageFile);
    }

}
