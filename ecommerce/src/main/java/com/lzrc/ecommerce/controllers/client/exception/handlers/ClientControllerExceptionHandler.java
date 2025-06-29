package com.lzrc.ecommerce.controllers.client.exception.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.lzrc.ecommerce.services.client.session.exceptions.ClientSessionIsInvalidException;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class ClientControllerExceptionHandler {

    @Autowired
    HttpSession session;

    @ExceptionHandler(exception = ClientSessionIsInvalidException.class)
    ModelAndView auth(){
        ModelAndView mv = new ModelAndView("redirect:/client/login");
        session.invalidate();
        return mv;
    }
}
