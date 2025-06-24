package com.lzrc.ecommerce.security.users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientDetails implements UserDetails {

    private String cpf;
    private String password;

    private GrantedAuthority getAuthority(String authority){
        return new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return authority;
            }
            
        };
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(
            getAuthority("ROLE_CLIENT")
        );
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return cpf;
    }

}
