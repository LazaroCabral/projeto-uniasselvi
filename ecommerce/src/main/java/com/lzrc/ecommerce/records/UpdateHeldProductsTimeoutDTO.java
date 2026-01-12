package com.lzrc.ecommerce.records;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateHeldProductsTimeoutDTO {

    @NotNull(message = "O valor de expiração de produtos pegos não podem ser nulos!!")
    private Long heldProductsTimeout;

    public UpdateHeldProductsTimeoutDTO(Long heldProductsTimeout){
        this.heldProductsTimeout = heldProductsTimeout;
    }

}
