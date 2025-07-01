package com.lzrc.ecommerce.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("admin-login-controller")
@RequestMapping("/admin")
public class LoginController {

    @GetMapping("/login")
    public String adminLogin(){
        return "admin/login.html";
    }

    @GetMapping("/login-success")
    public String adminLoginPost(){
        return "redirect:/admin/products";
    }
    
}
