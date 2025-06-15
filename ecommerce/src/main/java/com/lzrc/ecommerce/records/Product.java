package com.lzrc.ecommerce.records;

import java.math.BigDecimal;

public record Product(String sku,String name,
    String description,BigDecimal price) {

}
