package com.lzrc.ecommerce.controllers.admin.application.management;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lzrc.ecommerce.records.UpdateHeldProductsTimeoutDTO;
import com.lzrc.ecommerce.services.application.management.ApplicationManagementService;

import jakarta.validation.Valid;

@RequestMapping("/admin/application/management/heldproductstimeout")
@Controller
public class HoldProductsExpirationTimeController {

    @Autowired
    ApplicationManagementService applicationManagementService;

    @PostMapping("/update")
    public ModelAndView update(@Valid UpdateHeldProductsTimeoutDTO updateHeldProductsTimeoutDTO,
        BindingResult bindingResult, RedirectAttributes redirectAttributes){
        ModelAndView mv = new ModelAndView("redirect:/admin/application/management/home");
        if(bindingResult.hasErrors()){
            List<String> errorMessages = new ArrayList<>(); 
            bindingResult.getAllErrors().forEach( error -> {
                errorMessages.add(error.getDefaultMessage());
            });
            redirectAttributes.addFlashAttribute("dashboardErrors", errorMessages);
            return mv;
        }
        applicationManagementService.updateHeldProductsTimeout(
            updateHeldProductsTimeoutDTO.getHeldProductsTimeout());
        return mv;
    }

}
