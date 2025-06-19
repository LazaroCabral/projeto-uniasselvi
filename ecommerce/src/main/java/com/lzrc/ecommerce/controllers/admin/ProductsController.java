package com.lzrc.ecommerce.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.db.repositories.ProductRepository;
import com.lzrc.ecommerce.records.ProductRecord;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class ProductsController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/add-product")
    public String addProduct(){
        return "admin/products/add-product.html";
    }

    @PostMapping("/add-product")
    @Transactional
    public ModelAndView addProductPost(@Valid ProductRecord productRecord, BindingResult bindingResult){
        ModelAndView mv = new ModelAndView("admin/products/add-product.html");
        if(bindingResult.hasErrors()){
            return mv;
        }
        Product product = new Product(productRecord.sku(), productRecord.name(), 
            productRecord.description(), productRecord.price());
        productRepository.save(product);
        mv.addObject("success", Boolean.valueOf(true));
        return mv;
    }

}
