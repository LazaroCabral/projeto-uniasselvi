package com.lzrc.ecommerce.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lzrc.ecommerce.db.entities.PurchaseRecord;

public interface PurchaseRecordsRepository extends JpaRepository<PurchaseRecord,Long> {

}
