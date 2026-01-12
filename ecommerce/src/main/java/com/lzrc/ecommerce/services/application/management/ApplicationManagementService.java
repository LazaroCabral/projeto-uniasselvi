package com.lzrc.ecommerce.services.application.management;

public interface ApplicationManagementService {

    Long getHeldProductsTimeout();

    void updateHeldProductsTimeout(Long heldProductsTimeout);

}
