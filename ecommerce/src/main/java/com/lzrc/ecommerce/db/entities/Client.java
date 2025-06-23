package com.lzrc.ecommerce.db.entities;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Table(name = "clients")
@Getter
@AllArgsConstructor
public class Client {

    @Id
    private String cpf;
    private String name;
    private String email;
    private String password;
    private BigDecimal accountBalance;

    public Client(){}
}
