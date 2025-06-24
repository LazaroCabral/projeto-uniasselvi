package com.lzrc.ecommerce.security.filters;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

import com.lzrc.ecommerce.db.entities.Admin;
import com.lzrc.ecommerce.db.repositories.AdminRepository;
import com.lzrc.ecommerce.security.users.AdminDetails;

@Configuration
public class AdminFilter {

    @Autowired
    AdminRepository adminRepository;

    @Bean
    SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception{
        http
        .securityMatcher("/admin/**")
        .authorizeHttpRequests(authorize -> authorize
            .anyRequest().hasRole("ADMIN")
        )
        .csrf(csrf -> csrf.disable())
        .formLogin(form -> 
            form.loginPage("/admin/login").permitAll()).
        userDetailsService(userDetailsService());
        return http.build();
    }

    UserDetailsService userDetailsService(){
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Optional<Admin> optionalAdmin = adminRepository.findById(username);
                if(optionalAdmin.isPresent()){
                    Admin admin = optionalAdmin.get();
                    return new AdminDetails(admin.getCpf(),
                    admin.getPassword());
                } else {
                    throw new UsernameNotFoundException("Admin not found");
                }
            }
            
        };
    }

}
