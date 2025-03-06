package com.world.haymon.libraryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(config -> {
                    config.loginPage("/login");
                })
                .httpBasic(Customizer.withDefaults()) // to postman auth
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login/**").permitAll();
                    authorize.requestMatchers("/autores/**").hasRole("ADMIN");
                    authorize.requestMatchers("/livros/**").hasAnyRole("ADMIN", "USER");

                    authorize.anyRequest().authenticated(); // end
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10); // generate different hash when encoding
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {

        UserDetails user1 = User.builder()
                .username("usuario")
                .password(encoder.encode("123"))
                .roles("USER")
                .build();

        UserDetails user2 = User.builder()
                .username("admin")
                .password(encoder.encode("321"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }
}