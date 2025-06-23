package com.lzrc.ecommerce.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.db.repositories.ProductRepository;
import com.lzrc.ecommerce.records.ProductRecord;
import com.lzrc.ecommerce.records.response.ProductRecordResponse;

@Controller("public-products-controller")
public class ProductsController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/products")
    public ModelAndView home(Pageable pageable){
        ModelAndView mv = new ModelAndView("products.html");
        Page<ProductRecordResponse> products = productRepository
            .findAllBy(pageable);
        mv.addObject("products", products.getContent());
        return mv;
    }

    @GetMapping("/product/{id}")
    public ModelAndView product(@PathVariable("id") String productId){
        ModelAndView mv = new ModelAndView("product.html");
        Optional<Product> productOptinal=productRepository.findById(productId);
        if(productOptinal.isPresent()){
            Product product = productOptinal.get();
            ProductRecord productRecordResponse = new ProductRecord(
                product.getSku(), product.getName(), product.getDescription()
                , product.getPrice());
            mv.addObject("product", productRecordResponse);
        } else {
            mv.addObject("productIsFound", Boolean.FALSE);
        }
        return mv;
    }
}
