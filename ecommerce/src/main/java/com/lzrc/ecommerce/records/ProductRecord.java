package com.lzrc.ecommerce.records;

import java.math.BigDecimal;

public record ProductRecord(String sku,String name,
    String description,BigDecimal price) {

}
