package com.lzrc.ecommerce.services.product.purchase;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.db.repositories.ProductRepository;
import com.lzrc.ecommerce.services.client.exceptions.InsufficientBalanceException;
import com.lzrc.ecommerce.services.client.session.ClientSessionService;
import com.lzrc.ecommerce.services.client.session.exceptions.ClientSessionIsInvalidException;
import com.lzrc.ecommerce.services.product.exceptions.ProductNotFoundException;
import com.lzrc.ecommerce.services.product.exceptions.ProductNotHeldException;

import jakarta.servlet.http.HttpSession;

@Service
public class ProductPurchaseServiceImpl implements ProductPurchaseService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ClientSessionService clientSessionService;

    @Autowired
    HttpSession session;

    private boolean validateHeldProduct(String sku, HeldProduct heldProduct){
        if(heldProduct != null && 
            heldProduct.getProduct().getSku().equals(sku)){
                return true;
            } else {return false;}
    }

    @Override
    public void buyProduct(String sku) throws InsufficientBalanceException, ClientSessionIsInvalidException, ProductNotHeldException {
        HeldProduct heldProduct = (HeldProduct) session.getAttribute("heldProduct");
        if(validateHeldProduct(sku, heldProduct)){
            BigDecimal price = heldProduct.getProduct().getPrice();
            clientSessionService.debit(price);
            
        } else {throw new ProductNotHeldException();}
    }

    @Override
    public Product holdProduct(String sku) throws ProductNotFoundException {
        Optional<Product> optionalProduct =  productRepository.findById(sku);
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            HeldProduct heldProduct = new HeldProduct(product);
            session.setAttribute("heldProduct", heldProduct);
            return product;

        } else {throw new ProductNotFoundException();}
    }

}
