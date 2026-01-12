package com.lzrc.ecommerce.records;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ProductDTO{

    @Size(max = 14, message = "O sku não pode ser maior que 14 caracteres!")
    @NotBlank(message = "O sku não pode ser nulo!")
    String sku;

    @Size(max = 60, message = "O nome não pode ser maior que 60 caracteres!")
    @NotBlank(message = "O nome não pode ser nulo!")
    String name;

    @Size(max = 255, message = "A descrição não pode ser maior que 255 caracteres!")
    @NotBlank(message = "A descrição não pode ser nula!")
    String description;

    @NotNull(message = "O preço não pode ser nulo!")
    BigDecimal price;

    @NotNull(message = "O estoque não pode ser nulo!")
    Long availableStock;

    private void setAvailableStockOrSetDefaultValueIfIsNull(Long availableStock){
        if(availableStock == null){
            this.availableStock = 0L;
        } else {
            this.availableStock = availableStock;
        }
    }

    public ProductDTO(
        String sku, String name, String description,
            BigDecimal price,Long availableStock) {
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.price = price;
        setAvailableStockOrSetDefaultValueIfIsNull(availableStock);
    }


}
