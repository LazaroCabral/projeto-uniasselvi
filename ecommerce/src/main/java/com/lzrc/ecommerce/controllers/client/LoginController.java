package com.lzrc.ecommerce.controllers.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client")
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "clients/login.html";
    }

}
