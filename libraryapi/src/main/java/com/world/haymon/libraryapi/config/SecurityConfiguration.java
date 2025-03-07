package com.world.haymon.libraryapi.config;

import com.world.haymon.libraryapi.security.CustomUserDetailsService;
import com.world.haymon.libraryapi.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                    authorize.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll();
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
    public UserDetailsService userDetailsService(PasswordEncoder encoder, UsuarioService usuarioService) {

        UserDetails user = User.builder()
                .username("memory")
                .password(encoder.encode("memory"))
                .roles("USER")
                .build();

        //return new InMemoryUserDetailsManager(user);

        return new CustomUserDetailsService(usuarioService);
    }
}