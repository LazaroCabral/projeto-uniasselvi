package com.lzrc.ecommerce.controllers.admin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.records.ProductRecord;
import com.lzrc.ecommerce.records.response.ProductRecordResponse;
import com.lzrc.ecommerce.services.product.ProductService;
import com.lzrc.ecommerce.services.product.exceptions.ProductAlreadyExistsException;
import com.lzrc.ecommerce.services.product.exceptions.ProductNotFoundException;
import com.lzrc.ecommerce.services.product.image.exceptions.InvalidImageFormatException;
import com.lzrc.ecommerce.services.product.image.exceptions.SaveImageException;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class ProductsController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public ModelAndView home(Pageable pageable, @RequestParam(required = false) String name){
        ModelAndView mv = new ModelAndView("admin/products/products.html");
        Page<ProductRecordResponse> products;
        if(name != null){
            products = productService.searchProducts(name, pageable);
             mv.addObject("searchName", name);
        } else {
            products = productService.findAllProducts(pageable);
        }
        mv.addObject("products", products.getContent());
        mv.addObject("page", products);
        return mv;
    }

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

        try {
            productService.insert(product);
        } catch (ProductAlreadyExistsException e) {
            mv.addObject("errorMessage", "Um produto com este SKU já existe!");
            mv.addObject("success", Boolean.FALSE);
            return mv;
        }
        
        if(productImage.isEmpty() == false) {
            try {
                productService.saveProductImage(productImage, product);
                mv.addObject("success", Boolean.TRUE);
            } catch (SaveImageException e) {
                mv.addObject("errorMessage", "Não foi possivel salvar a imagem!");
            } catch (InvalidImageFormatException e) {
                mv.addObject("errorMessage", "O formato da imagem é invalido! O formato deve ser png!");
            }
        }
        mv.addObject("success", Boolean.TRUE);
        return mv;
    }

    @GetMapping("/update-product/{sku}")
    public ModelAndView updateProduct(@PathVariable String sku){
        ModelAndView mv = new ModelAndView("admin/products/update-product.html");

        Optional<ProductRecordResponse> optionalProduct = productService.findById(sku);

        if(optionalProduct.isPresent()){
            mv.addObject("product", optionalProduct.get());
            mv.addObject("success", Boolean.TRUE);
        } else {
            mv.addObject("success", Boolean.FALSE);
            mv.addObject("errorMessage", "Produto não encontrado!");
        }
        return mv;
    }

    @PostMapping(path = "/update-product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ModelAndView updateProductPost(@RequestParam("product-image") MultipartFile productImage,
            @Valid ProductRecord productRecord, BindingResult bindingResult) {
                
        ModelAndView mv = new ModelAndView("redirect:/admin/products");
        if(bindingResult.hasErrors()){
            return mv;
        }

        Product product = new Product(productRecord.sku(), productRecord.name(), 
            productRecord.description(), productRecord.price(), productRecord.availableStock());

        try {
            productService.update(product);
        } catch (ProductNotFoundException e) {
            mv.addObject("errorMessage", "Produto não encontrado!");
            mv.addObject("success", Boolean.FALSE);
            return mv;
        }
        
        if(productImage.isEmpty() == false) {
            try {
                productService.saveProductImage(productImage, product);
                mv.addObject("success", Boolean.TRUE);
            } catch (SaveImageException e) {
                mv.addObject("warningMessage", "A imagem não pode ser salva!");
            } catch (InvalidImageFormatException e) {
                mv.addObject("warningMessage", "O formato de imagem é invalido. A imagem não pode ser salva!");
            }
        }
        mv.addObject("success", Boolean.TRUE);
        return mv;
    }

    @GetMapping("/delete-product/{sku}")
    public String deleteProduct(@PathVariable String sku, @RequestParam(name = "search-name" , required = false) String searchName){
        productService.delete(sku);
        if(searchName != null){
            return "redirect:/admin/products?name=".concat(searchName);
        } else{
            return "redirect:/admin/products";
        }
    }


}
