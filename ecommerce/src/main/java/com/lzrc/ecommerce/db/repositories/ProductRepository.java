package com.lzrc.ecommerce.db.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.records.response.ProductRecordResponse;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {

    Page<ProductRecordResponse> findAllBy(Pageable pageable);
    
}
