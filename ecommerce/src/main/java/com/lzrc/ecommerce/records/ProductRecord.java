package com.lzrc.ecommerce.records;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductRecord(
    @Size(max = 14, message = "O sku não pode ser maior que 14 caracteres!")
    @NotBlank(message = "O sku não pode ser nulo!")
    String sku,
    @Size(max = 60, message = "O nome não pode ser maior que 60 caracteres!")
    @NotBlank(message = "O nome não pode ser nulo!")
    String name,
    @Size(max = 255, message = "A descrição não pode ser maior que 255 caracteres!")
    @NotBlank(message = "A descrição não pode ser nula!")
    String description,
    @NotNull(message = "O preço não pode ser nulo!")
    BigDecimal price) {

}
