package com.thoughtprocess.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    //Allows configuring of web security for specific HTTP requests
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //Method to configure how requests should be authorized - Lambda to configure authorization rules
        http.authorizeRequests(expressionInterceptUrlRegistry ->
                expressionInterceptUrlRegistry
                        //Allows all requests to be permitted without authentication
                        .anyRequest()
                        .permitAll())
                        //Disables Cross-Site Request Forgery
                        .csrf(AbstractHttpConfigurer::disable);
        return http.build();
        // This method allows, all requests, without authentication and disables CSRF protection
    }
}
