package com.lzrc.ecommerce.services.client.session;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.db.repositories.ProductRepository;
import com.lzrc.ecommerce.services.client.ClientService;
import com.lzrc.ecommerce.services.client.exceptions.ClientNotFoundException;
import com.lzrc.ecommerce.services.client.exceptions.InsufficientBalanceException;
import com.lzrc.ecommerce.services.client.session.exceptions.ClientSessionIsInvalidException;
import com.lzrc.ecommerce.services.product.exceptions.ProductNotFoundException;
import com.lzrc.ecommerce.services.product.exceptions.ProductNotHeldException;
import com.lzrc.ecommerce.services.product.purchase.HeldProduct;
import com.lzrc.ecommerce.services.product.purchase.ProductPurchaseService;

import jakarta.servlet.http.HttpSession;

@Service
public class ClientSessionServiceImpl implements ClientSessionService{

    @Autowired
    ClientService clientService;
    
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductPurchaseService purchaseService;

    @Autowired
    HttpSession session;

    private void invalidateSession(){
        SecurityContextHolder.getContext()
            .getAuthentication().setAuthenticated(false);
    }

    @Override
    public void debit(BigDecimal value) throws InsufficientBalanceException, ClientSessionIsInvalidException {
        String cpf = SecurityContextHolder
            .getContext().getAuthentication().getName();
        try {
            clientService.debit(cpf, value);
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
            invalidateSession();
            throw new ClientSessionIsInvalidException();
        }
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

    @Override
    public void buyProduct(String sku) throws InsufficientBalanceException, ProductNotHeldException, ClientSessionIsInvalidException {
        HeldProduct heldProduct = (HeldProduct) session.getAttribute("heldProduct");
        if(heldProduct != null && 
            heldProduct.getProduct().getSku().equals(sku)){
            purchaseService.buyProduct(heldProduct, this);
            
        } else {throw new ProductNotHeldException();}
    }

   
}
