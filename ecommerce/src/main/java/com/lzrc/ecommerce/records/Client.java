package com.lzrc.ecommerce.records;

import java.math.BigDecimal;

public record Client(String cpf,String name,String email,
    String password,BigDecimal accountBalance) {

}
