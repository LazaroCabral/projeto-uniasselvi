package com.lzrc.ecommerce.db.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
public class Product {

    @Id
    private String sku;
    private String name;
    private String description;
    private BigDecimal price;
    @Column(updatable = false)
    private Long availableStock;
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_version_id")
    private ProductVersion productVersion;

    public Product(){}

    public Product(String sku, String name, String description, BigDecimal price, Long availableStock) {
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableStock = availableStock;
    }



    public Product(String sku, String name, String description, BigDecimal price) {
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableStock=0L;
    };


}
