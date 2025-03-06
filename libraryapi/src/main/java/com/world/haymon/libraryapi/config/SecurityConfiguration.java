package com.world.haymon.libraryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity htpp) throws Exception {
        return htpp
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(config -> {
                    config.loginPage("/login").permitAll();
                })
                //.httpBasic(Customizer.withDefaults()) // to postman auth
                .authorizeHttpRequests(authorize -> {
                    authorize.anyRequest().authenticated();
                })
                .build();
    }
}