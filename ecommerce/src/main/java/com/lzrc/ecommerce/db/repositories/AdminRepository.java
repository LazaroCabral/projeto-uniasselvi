package com.lzrc.ecommerce.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lzrc.ecommerce.db.entities.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin,String>{

}
