package com.lzrc.ecommerce.db.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Table(name = "admins")
@AllArgsConstructor
@Getter
public class Admin {

    @Id
    private String cpf;
    private String name;
    private String password;

    public Admin(){}

    public void setPassword(String password) {
        this.password = password;
    }

}
