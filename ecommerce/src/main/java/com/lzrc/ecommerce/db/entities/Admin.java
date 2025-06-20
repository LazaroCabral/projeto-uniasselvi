package com.lzrc.ecommerce.db.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "admins")
@AllArgsConstructor
public class Admin {

    @Id
    private String cpf;
    private String name;
    private String password;

    public Admin(){}

}
