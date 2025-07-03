package com.lzrc.ecommerce.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.lzrc.ecommerce.db.entities.Admin;
import com.lzrc.ecommerce.db.repositories.AdminRepository;
import com.lzrc.ecommerce.services.admin.AdminService;

@Component
public class InitialDatabaseSeeder implements CommandLineRunner {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AdminService adminService;

    @Override
    public void run(String... args) throws Exception {
        Long hasAdmin = adminRepository.count();
        if(hasAdmin == 0){
            Admin admin = new Admin("00000000000", "admin", "admin1234");
            adminService.update(admin);
        }
    }

}
