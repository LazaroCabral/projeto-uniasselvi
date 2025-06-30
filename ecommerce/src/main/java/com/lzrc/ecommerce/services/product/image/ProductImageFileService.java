package com.lzrc.ecommerce.services.product.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lzrc.ecommerce.services.product.image.exceptions.InvalidImageFormatException;
import com.lzrc.ecommerce.services.product.image.exceptions.SaveImageException;

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

    private File getImageFile(String sku){
        String imageString = imagesPath.concat("/").concat(sku).concat(".png");
        return new File(imageString);
    }

    private void writeImage(byte[] productImage,File file) throws SaveImageException{
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

    private void createImage(byte[] image, File imageFile) throws SaveImageException{
        try {
            imageFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            throw new SaveImageException();
        }
        writeImage(image, imageFile);
    }

    public void saveImage(MultipartFile image, String sku) throws InvalidImageFormatException, SaveImageException {
        imageIsValid(image);
        try {
            createImage(image.getBytes(), getImageFile(sku));
        } catch (SaveImageException | IOException e) {
            e.printStackTrace();
            throw new SaveImageException();
        }
    }
    
    public void deleteImageIfExists(String sku){
        File imageFile = getImageFile(sku);
        if(imageFile.exists()){
            imageFile.delete();
        }
    }

}
