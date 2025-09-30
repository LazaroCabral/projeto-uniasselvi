package com.lzrc.ecommerce.controllers.admin.application.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lzrc.ecommerce.records.UpdateHeldProductsTimeoutDTO;
import com.lzrc.ecommerce.services.application.management.ApplicationManagementService;

@RequestMapping("/admin/application/management")
@Controller
public class ManagementHomeController {

    @Autowired
    ApplicationManagementService applicationManagementService;

    @GetMapping("/home")
    public ModelAndView home(RedirectAttributes redirectAttributes){
        ModelAndView mv = new ModelAndView("admin/application/management/home.html");
        mv.addObject(new UpdateHeldProductsTimeoutDTO(
            applicationManagementService.getHeldProductsTimeout()));
        return mv;
    }
}
