package com.lzrc.ecommerce.services.product.purchase;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.db.repositories.ProductRepository;
import com.lzrc.ecommerce.records.response.ProductRecordResponse;
import com.lzrc.ecommerce.services.client.exceptions.InsufficientBalanceException;
import com.lzrc.ecommerce.services.client.session.ClientSessionService;
import com.lzrc.ecommerce.services.product.ProductService;
import com.lzrc.ecommerce.services.product.exceptions.InsufficientStockException;
import com.lzrc.ecommerce.services.product.exceptions.ProductNotFoundException;
import com.lzrc.ecommerce.services.product.exceptions.ProductNotHeldException;

import jakarta.servlet.http.HttpSession;

@Service
public class ProductPurchaseServiceImpl implements ProductPurchaseService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Autowired
    ClientSessionService clientSessionService;

    @Autowired
    HttpSession session;

    @Value("${products.held-product-time-limit}")
    Long heldProductTimeLimit;

    private boolean validateHeldProduct(String sku, HeldProduct heldProduct){
        if(heldProduct != null && 
            heldProduct.getProduct().getSku().equals(sku)){
                Long heldProductTime = System.currentTimeMillis() - heldProduct.getHeldAt();
                if (heldProductTime > heldProductTimeLimit) {
                    return false;
                }
                return true;
            } else {return false;}
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void buyProduct(String sku) throws InsufficientBalanceException, ProductNotHeldException, InsufficientStockException, ProductNotFoundException {
        HeldProduct heldProduct = (HeldProduct) session.getAttribute("heldProduct");
        if(validateHeldProduct(sku, heldProduct)){
            BigDecimal price = heldProduct.getProduct().getPrice();
            clientSessionService.debit(price);
            productService.reduceStock(sku, 1L);

        } else {throw new ProductNotHeldException();}
    }

    @Override
    public ProductRecordResponse holdProduct(String sku) throws ProductNotFoundException {
        Optional<Product> optionalProduct =  productRepository.findById(sku);
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            HeldProduct heldProduct = new HeldProduct(product, System.currentTimeMillis());
            session.setAttribute("heldProduct", heldProduct);
            return new ProductRecordResponse(product.getSku(), product.getName(),
                product.getDescription(), product.getPrice(), product.getAvailableStock());

        } else {throw new ProductNotFoundException();}
    }

}
