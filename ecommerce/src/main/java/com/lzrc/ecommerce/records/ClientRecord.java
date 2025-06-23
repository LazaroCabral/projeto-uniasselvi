package com.lzrc.ecommerce.records;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClientRecord(
    
    @NotBlank(message = "O cpf não pode ser nulo!")
    @Size(min = 11,max = 11, message = "O cpf deve possuir 11 numeros!")
    String cpf,

    @NotBlank(message = "O nome não pode ser nulo!")
    @Size(max = 60, message = "O nome não pode ser maior que 60 caracteres!")
    String name,

    @NotBlank(message = "O email não pode ser nulo!")
    @Size(max = 40, message = "O email não pode ser maior que 4 caracteres!")
    String email,

    @NotBlank(message = "A senha não pode ser nula!")
    @Size(max = 30, message = "A senha não pode ser maior que 30 caracteres!")
    String password,

    BigDecimal accountBalance) {

}
