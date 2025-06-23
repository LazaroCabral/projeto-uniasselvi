package com.lzrc.ecommerce.db.repositories.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.records.response.ProductRecordResponse;

@Repository
public interface CustomProductRepository extends CrudRepository<Product,String>{

     Page<ProductRecordResponse> findAll(Pageable pageable);

}
