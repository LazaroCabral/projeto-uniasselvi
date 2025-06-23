package com.lzrc.ecommerce.controllers.client;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lzrc.ecommerce.db.entities.Client;
import com.lzrc.ecommerce.records.ClientRecord;
import com.lzrc.ecommerce.services.client.ClientService;
import com.lzrc.ecommerce.services.client.exceptions.ClientAlreadyExistsException;

import jakarta.validation.Valid;

@Controller("client-account-controller")
@RequestMapping("/client")
public class AccountController {

    @Autowired
    ClientService clientService;

    @GetMapping("/add-client")
    public String addClientGet(){
        return "clients/add-client.html";
    }

    @PostMapping("/add-client")
    public ModelAndView addClientPost(@Valid ClientRecord clientRecord, BindingResult bindingResult){
        ModelAndView mv = new ModelAndView("clients/add-client.html");
        if(bindingResult.hasErrors()){
            return mv;
        }
        Client client = new Client(
            clientRecord.cpf(),clientRecord.name()
            , clientRecord.email(), clientRecord.password(), BigDecimal.ZERO);
        try {
            clientService.insert(client);
            mv.addObject("success", Boolean.TRUE);
        } catch (ClientAlreadyExistsException e) {
            mv.addObject("clientAlreadyExists", Boolean.TRUE);
            mv.addObject("success", Boolean.FALSE);
            e.printStackTrace();
        }
        return mv;
    }
}
