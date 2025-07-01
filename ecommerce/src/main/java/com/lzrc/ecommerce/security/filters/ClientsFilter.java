package com.lzrc.ecommerce.security.filters;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

import com.lzrc.ecommerce.db.entities.Client;
import com.lzrc.ecommerce.db.repositories.ClientRepository;
import com.lzrc.ecommerce.security.users.ClientDetails;


@Configuration
public class ClientsFilter {

    @Autowired
    ClientRepository clientRepository;

    @Bean
    SecurityFilterChain clientsFilterChain(HttpSecurity http) throws Exception{
        http.
        securityMatcher("/client/**")
        .authorizeHttpRequests(
            authorize -> authorize
                .requestMatchers(HttpMethod.GET,
                "/client/add-client")
                .permitAll()
                .requestMatchers(HttpMethod.POST, 
                "/client/add-client")
                .permitAll()
                .anyRequest().hasRole("CLIENT")
        )
        .csrf(csrf -> csrf.disable())
        .formLogin(form -> form.loginPage("/client/login")
            .defaultSuccessUrl("/client/login-success").permitAll())
        .logout( logout -> logout.logoutUrl("/client/logout"))
        .userDetailsService(clientDetails());

        return http.build();
    }

    UserDetailsService clientDetails(){
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Optional<Client> optionalClient = clientRepository.findById(username);
                if(optionalClient.isPresent()){
                    Client client = optionalClient.get();
                    return new ClientDetails(client.getCpf(),client.getPassword());
                } else{
                    throw new UsernameNotFoundException("User not found");
                }
            }
            
        };
    }
    

}
