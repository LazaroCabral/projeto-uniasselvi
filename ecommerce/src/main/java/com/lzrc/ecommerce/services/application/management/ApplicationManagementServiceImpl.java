package com.lzrc.ecommerce.services.application.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lzrc.ecommerce.services.application.management.holdproducts.TimeoutManager;

@Service
public class ApplicationManagementServiceImpl implements ApplicationManagementService {

    @Autowired
    TimeoutManager timeoutManager;

    public Long getHeldProductsTimeout(){
        return timeoutManager.getTimeout();
    }
    
}
