package com.lzrc.ecommerce.db.repositories.custom;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.records.response.ProductRecordResponse;

import jakarta.persistence.LockModeType;

@org.springframework.stereotype.Repository
public interface CustomProductRepository extends Repository<Product,String>{

     Optional<ProductRecordResponse> findById(String sku);     

     Page<ProductRecordResponse> findAll(Pageable pageable);

     Page<ProductRecordResponse> findByNameContainingIgnoreCase(String name, Pageable pageable);

     @Query("SELECT p FROM Product p WHERE p.productVersion IN " +
          "(SELECT pr.productVersion FROM PurchaseRecord pr WHERE pr.purchasedAt BETWEEN ?1 AND ?2 " +
                    "GROUP BY pr.productVersion HAVING count(pr.productVersion) >= ?3)")
     List<ProductRecordResponse> findMostPurchasedProducts(LocalDateTime since, LocalDateTime until, Long quantityPurchased, Limit size);

     @Lock(LockModeType.PESSIMISTIC_WRITE)
     @Query("SELECT p FROM Product p WHERE p.sku = ?1")
     Optional<Product> findByIdWithWriteLock(String sku);

     @Query("SELECT p FROM Product p JOIN FETCH p.productVersion WHERE p.sku = ?1")
     Optional<Product> findByIdAndFetchVersion(String sku);     

     @Modifying
     @Query("UPDATE Product p SET p.availableStock = p.availableStock + ?2 WHERE p.sku = ?1")
     void incrementAvailableStock(String sku, Long availableStock);

     @Modifying
     @Transactional(propagation = Propagation.MANDATORY)
     @Query("UPDATE Product p SET "+ 
          "p.name = ?2, "+
          "p.description = ?3, "+
          "p.price = ?4 "+
          "WHERE p.sku = ?1")
     int updateCommonFields(String sku, String name, String description, BigDecimal price);

}
