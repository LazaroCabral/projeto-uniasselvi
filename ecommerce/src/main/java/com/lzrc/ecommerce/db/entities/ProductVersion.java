package com.lzrc.ecommerce.db.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_versions")
@Getter
@Setter
public class ProductVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sku;
    private String name; 
    private BigDecimal price;
    private String description;
    private LocalDateTime updatedAt;

    public ProductVersion(){}

    public ProductVersion(String sku, String name, BigDecimal price, String description, LocalDateTime updatedAt) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.description = description;
        this.updatedAt = updatedAt;
    }



}
