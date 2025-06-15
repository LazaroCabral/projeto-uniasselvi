package com.lzrc.ecommerce.records;

import java.math.BigDecimal;

public record ClientRecord(String cpf,String name,String email,
    String password,BigDecimal accountBalance) {

}
