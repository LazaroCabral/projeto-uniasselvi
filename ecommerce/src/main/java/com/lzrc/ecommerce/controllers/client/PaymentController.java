package com.lzrc.ecommerce.controllers.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.records.response.ProductRecordResponse;
import com.lzrc.ecommerce.services.client.exceptions.InsufficientBalanceException;
import com.lzrc.ecommerce.services.client.session.ClientSessionService;
import com.lzrc.ecommerce.services.client.session.exceptions.ClientSessionIsInvalidException;
import com.lzrc.ecommerce.services.product.exceptions.ProductNotFoundException;
import com.lzrc.ecommerce.services.product.exceptions.ProductNotHeldException;

@Controller
@RequestMapping("client/")
public class PaymentController {

    @Autowired
    ClientSessionService clientSessionService;

    @GetMapping("/pay/{sku}")
    public ModelAndView paymentGet(@PathVariable String sku){
        ModelAndView mv = new ModelAndView("clients/pay.html");
        try {
            Product product = clientSessionService.holdProduct(sku);
            mv.addObject("product", 
                new ProductRecordResponse(product.getSku(), product.getName(), 
                    product.getDescription(), product.getPrice()));
            mv.addObject("success", Boolean.TRUE);
        } catch (ProductNotFoundException e) {
            e.printStackTrace();
            mv.addObject("productNotFound", Boolean.TRUE);
            mv.addObject("success", Boolean.FALSE);
        }
        return mv;
    }

    @PostMapping("/pay")
    @Transactional(propagation = Propagation.NESTED)
    public ModelAndView paymentPost(String sku){
        ModelAndView mv = new ModelAndView("clients/pay.html");
        mv.addObject("buySuccess", Boolean.FALSE);

        try {
            clientSessionService.buyProduct(sku);
            mv.addObject("buySuccess", Boolean.TRUE);
        } catch (InsufficientBalanceException e) {
            mv.addObject("insufficientBalance", Boolean.TRUE);
        } catch (ProductNotHeldException e) {
            mv.addObject("productHeldNotFound", Boolean.TRUE);
        } catch (ClientSessionIsInvalidException e) {
            mv.setViewName("redirect:/public/products");
        }
        
        return mv;
    }


}
