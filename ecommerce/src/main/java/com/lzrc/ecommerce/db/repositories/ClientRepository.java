package com.lzrc.ecommerce.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lzrc.ecommerce.db.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client,String> {

}
