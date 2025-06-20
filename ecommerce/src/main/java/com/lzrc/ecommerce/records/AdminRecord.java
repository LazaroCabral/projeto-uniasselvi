package com.lzrc.ecommerce.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminRecord(
    
    @NotBlank(message = "O cpf não pode ser nulo!")
    @Size(max = 11, min = 11, message = "O cpf deve ter 11 caracteres!")
    String cpf,
    @NotBlank(message = "O nome não pode ser nulo!")
    @Size(max = 60, message = "O nome não pode ser maior que 60 caracteres!")
    String name,
    @NotBlank(message = "O senha não pode ser nula!")
    @Size(max = 60, message = "O senha não pode ser maior que 60 caracteres!")
    String password) {

}
