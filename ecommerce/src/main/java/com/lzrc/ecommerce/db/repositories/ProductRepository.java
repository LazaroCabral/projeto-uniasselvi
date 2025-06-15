package com.lzrc.ecommerce.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lzrc.ecommerce.db.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {

}
