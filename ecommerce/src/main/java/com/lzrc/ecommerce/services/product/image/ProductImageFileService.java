package com.lzrc.ecommerce.services.product.image;

import java.io.File;
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

    private File getImageFile(String sku){
        String imageString = imagesPath.concat("/").concat(sku).concat(".png");
        return new File(imageString);
    }

    public void saveImage(MultipartFile image, String sku) throws InvalidImageFormatException, SaveImageException {
        ProductImageFileUtils.imageIsValid(image);
        try {
            File imageFile=getImageFile(sku);
            if(imageFile.exists()){
                ProductImageFileUtils.writeImage(image.getBytes(), imageFile);
            }else{
                ProductImageFileUtils.createImage(image.getBytes(), imageFile);
            }
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
