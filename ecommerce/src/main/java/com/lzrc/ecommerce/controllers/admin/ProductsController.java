package com.lzrc.ecommerce.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.db.repositories.ProductRepository;
import com.lzrc.ecommerce.records.ProductRecord;
import com.lzrc.ecommerce.services.product.ProductService;
import com.lzrc.ecommerce.services.product.exceptions.InvalidImageFormatException;
import com.lzrc.ecommerce.services.product.exceptions.SaveImageException;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class ProductsController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @GetMapping("/add-product")
    public String addProduct(){
        return "admin/products/add-product.html";
    }

    @PostMapping(path = "/add-product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ModelAndView addProductPost(@RequestParam("product-image") MultipartFile productImage,
            @Valid ProductRecord productRecord, BindingResult bindingResult) {
                
        ModelAndView mv = new ModelAndView("admin/products/add-product.html");
        if(bindingResult.hasErrors()){
            return mv;
        }

        Product product = new Product(productRecord.sku(), productRecord.name(), 
            productRecord.description(), productRecord.price(), productRecord.availableStock());

        productService.save(product);
        
        if(productImage.isEmpty() == false) {
            try {
                productService.saveProductImage(productImage, product);
                mv.addObject("success", Boolean.TRUE);
            } catch (SaveImageException e) {
                mv.addObject("imageIsSaved", Boolean.FALSE);
            } catch (InvalidImageFormatException e) {
                mv.addObject("invalidImageFormat", Boolean.TRUE);
            }
        }
        mv.addObject("success", Boolean.TRUE);
        return mv;
    }

}
