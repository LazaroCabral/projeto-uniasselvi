package com.lzrc.ecommerce.services.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lzrc.ecommerce.db.entities.Admin;
import com.lzrc.ecommerce.db.repositories.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private void encodeAdminPassword(Admin admin){
        admin.setPassword(
            passwordEncoder.encode(admin.getPassword())
        );
    }

    @Override
    public void update(Admin admin) {
        encodeAdminPassword(admin);
        adminRepository.deleteAll();
        adminRepository.save(admin);
    }


}
