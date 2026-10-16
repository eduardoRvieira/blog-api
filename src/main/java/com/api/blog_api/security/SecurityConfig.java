package com.api.blog_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET,
                                "/api/posts",
                                "/api/posts/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/api/newpost").permitAll()
                        .requestMatchers(HttpMethod.PUT,
                                "/api/posts/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/posts/{id}").hasRole("ADMIN")
                        .anyRequest().authenticated()
                ).httpBasic(httpBasic -> {})
                .csrf(csrf -> csrf.disable())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
