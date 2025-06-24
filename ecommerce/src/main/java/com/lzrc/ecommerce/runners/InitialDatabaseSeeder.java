package com.lzrc.ecommerce.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.lzrc.ecommerce.db.entities.Admin;
import com.lzrc.ecommerce.db.repositories.AdminRepository;

@Component
public class InitialDatabaseSeeder implements CommandLineRunner {

    @Autowired
    AdminRepository adminRepository;

    @Override
    public void run(String... args) throws Exception {
        Long hasAdmin = adminRepository.count();
        if(hasAdmin == 0){
            Admin admin = new Admin("00000000000", "admin", "{noop}admin1234");
            adminRepository.save(admin);
        }
    }

}
