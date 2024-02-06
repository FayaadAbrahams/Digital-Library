package org.thoughtprocess;

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
        //Method to configure how requests to be authorized
        http.authorizeRequests(expressionInterceptUrlRegistry ->
                //
                expressionInterceptUrlRegistry
                        .anyRequest()
                        .permitAll())
                        .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
