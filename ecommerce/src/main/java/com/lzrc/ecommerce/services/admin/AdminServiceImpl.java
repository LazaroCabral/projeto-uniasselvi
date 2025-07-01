package com.lzrc.ecommerce.services.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lzrc.ecommerce.db.entities.Admin;
import com.lzrc.ecommerce.db.repositories.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Override
    public void update(Admin admin) {
        admin.setPassword("{noop}".concat(admin.getPassword()));
        adminRepository.deleteAll();
        adminRepository.save(admin);
    }


}
