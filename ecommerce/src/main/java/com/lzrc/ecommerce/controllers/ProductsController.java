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

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.db.repositories.ProductRepository;
import com.lzrc.ecommerce.db.repositories.custom.CustomProductRepository;
import com.lzrc.ecommerce.records.response.ProductRecordResponse;

@Controller("public-products-controller")
@RequestMapping("/public")
public class ProductsController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomProductRepository customProductRepository;

    @GetMapping("/products")
    public ModelAndView home(Pageable pageable, @RequestParam(required = false) String name){
        ModelAndView mv = new ModelAndView("products.html");
        Page<ProductRecordResponse> products;
        if(name != null){
             products = customProductRepository.findByNameContainingIgnoreCase(name, pageable);
             mv.addObject("searchName", name);
        } else{
            products = customProductRepository.findAll(pageable);
        }
        mv.addObject("currentPage", products.getNumber());
        mv.addObject("totalPages", products.getTotalPages());
        mv.addObject("productsSize", products.getNumberOfElements());
        mv.addObject("products", products.getContent());
        return mv;
    }

    @GetMapping("/product/{id}")
    public ModelAndView product(@PathVariable("id") String productId){
        ModelAndView mv = new ModelAndView("product.html");
        Optional<Product> productOptinal=productRepository.findById(productId);
        if(productOptinal.isPresent()){
            Product product = productOptinal.get();
            ProductRecordResponse productRecordResponse = new ProductRecordResponse(
                product.getSku(), product.getName(), product.getDescription()
                , product.getPrice(), product.getAvailableStock());
            mv.addObject("product", productRecordResponse);
        } else {
            mv.addObject("productIsFound", Boolean.FALSE);
        }
        return mv;
    }
}
