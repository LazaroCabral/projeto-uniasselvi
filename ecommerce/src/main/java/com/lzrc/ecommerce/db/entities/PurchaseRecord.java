package com.lzrc.ecommerce.db.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "purchase_records")
@AllArgsConstructor
public class PurchaseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "client_cpf")
    private Client client;

    @OneToOne(optional = false)
    @JoinColumn(name = "product_version_id")
    private ProductVersion productVersion;

    private LocalDateTime purchasedAt;

    public PurchaseRecord(){}

    public PurchaseRecord(Client client, ProductVersion productVersion) {
        this.client = client;
        this.productVersion = productVersion;
        this.purchasedAt = LocalDateTime.now();
    }

    

}
