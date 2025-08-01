package com.lzrc.ecommerce.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lzrc.ecommerce.records.response.ProductRecordResponse;
import com.lzrc.ecommerce.services.product.ProductService;

@Controller("public-products-controller")
@RequestMapping("/public")
public class ProductsController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public ModelAndView home(Pageable pageable, @RequestParam(required = false) String name){
        ModelAndView mv = new ModelAndView("products.html");
        Page<ProductRecordResponse> products;
        if(name != null){
             products = productService.searchProducts(name, pageable);
             mv.addObject("searchName", name);
        } else{
            products = productService.findAllProducts(pageable);
        }
        mv.addObject("products", products.getContent());
        mv.addObject("page", products);
        return mv;
    }

    @GetMapping("/product/{id}")
    public ModelAndView product(@PathVariable("id") String productId){
        ModelAndView mv = new ModelAndView("product.html");
        Optional<ProductRecordResponse> productOptinal=productService.findById(productId);
        if(productOptinal.isPresent()){
            mv.addObject("product", productOptinal.get());
        } else {
            mv.addObject("productIsFound", Boolean.FALSE);
        }
        return mv;
    }
}
