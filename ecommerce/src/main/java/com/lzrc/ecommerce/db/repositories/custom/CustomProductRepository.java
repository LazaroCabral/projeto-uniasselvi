package com.lzrc.ecommerce.db.repositories.custom;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.records.response.ProductRecordResponse;

import jakarta.persistence.LockModeType;

@Repository
public interface CustomProductRepository extends CrudRepository<Product,String>{

     Page<ProductRecordResponse> findAll(Pageable pageable);

     Page<ProductRecordResponse> findByNameContainingIgnoreCase(String name, Pageable pageable);

     @Lock(LockModeType.PESSIMISTIC_WRITE)
     @Query("SELECT p FROM Product p WHERE p.sku = ?1")
     Optional<Product> findByIdWithWriteLock(String sku);

}
