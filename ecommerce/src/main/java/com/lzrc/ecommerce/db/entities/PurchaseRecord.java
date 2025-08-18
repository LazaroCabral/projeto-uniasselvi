package com.lzrc.ecommerce.db.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;

@Entity(name = "purchase_records")
@AllArgsConstructor
public class PurchaseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "client_cpf")
    private Client client;
    private String sku;
    private String name;
    private BigDecimal price;
    private LocalDateTime purchasedAt;

    public PurchaseRecord(){}

    public PurchaseRecord(Client client, String sku, String name, BigDecimal price) {
        this.client = client;
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.purchasedAt = LocalDateTime.now();
    }

    

}
