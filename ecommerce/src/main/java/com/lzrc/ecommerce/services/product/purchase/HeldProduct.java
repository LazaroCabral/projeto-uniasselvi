package com.lzrc.ecommerce.services.product.purchase;

import com.lzrc.ecommerce.db.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HeldProduct {

    Product product;
    Long heldAt;

}
