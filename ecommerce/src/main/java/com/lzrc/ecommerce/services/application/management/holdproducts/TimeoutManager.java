package com.lzrc.ecommerce.services.application.management.holdproducts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TimeoutManager {

    private Long timeout;

    public TimeoutManager(@Value("${products.held-product-time-limit}") Long timeout){
        this.timeout = timeout;
    }

    public synchronized void setTimeout(Long timeoutAsTimemillis){
        this.timeout = timeoutAsTimemillis;
    }

    public synchronized Long getTimeout(){
        return this.timeout;
    }

}
