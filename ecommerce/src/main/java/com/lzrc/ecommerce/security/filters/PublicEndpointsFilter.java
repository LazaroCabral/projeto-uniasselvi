package com.lzrc.ecommerce.security.filters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class PublicEndpointsFilter {

    @Bean
    SecurityFilterChain publicEndpointsFilterChain(HttpSecurity http) throws Exception{
        http
        .securityMatcher("/public/**")
        .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.GET, 
                "/public/**")
                .permitAll()
                .requestMatchers(HttpMethod.POST,
                "/public/**")
                .permitAll()
                .anyRequest().authenticated()
        );
        return http.build(); 
    }

    @Bean
    SecurityFilterChain staticFilesFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.GET,
                "/static/**")
                .permitAll()
                .anyRequest().authenticated()
        );
        return http.build();
    }


}
