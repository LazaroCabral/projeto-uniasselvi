package com.lzrc.ecommerce.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lzrc.ecommerce.db.entities.Admin;
import com.lzrc.ecommerce.records.AdminRecord;
import com.lzrc.ecommerce.services.admin.AdminService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AccountController {

    @Autowired
    AdminService adminService;

    @GetMapping("/update-account")
    public String addAdminGet(){
        return "admin/update-account.html";
    }

    @PostMapping("/update-account")
    public ModelAndView addAdminPost(@Valid AdminRecord adminRecord, BindingResult bindingResult){
        ModelAndView mv = new ModelAndView("admin/update-account.html");
        if(bindingResult.hasErrors()){
            return mv;
        }
        Admin admin = new Admin(adminRecord.cpf(), 
            adminRecord.name(), adminRecord.password());
        adminService.update(admin);
        mv.addObject("success", Boolean.TRUE);
        return mv;
    }

}
