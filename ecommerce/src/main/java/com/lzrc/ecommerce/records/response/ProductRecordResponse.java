package com.lzrc.ecommerce.records.response;

import java.math.BigDecimal;

public record ProductRecordResponse(
    String sku,
    String name,
    String description,
    BigDecimal price,
    Long availableStock) {

}
