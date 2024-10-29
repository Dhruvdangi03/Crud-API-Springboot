package com.crud_demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SpringSecurity {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Consider re-enabling CSRF if applicable
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/userLogin", "/sign-up", "/docs").permitAll() // Publicly accessible
                        .requestMatchers("/stud/**").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/teach/**").hasRole("TEACHER")
                        .anyRequest().authenticated()) // Fallback for other requests
                .formLogin(Customizer.withDefaults()) // You may want to use form login instead of basic authentication
                .httpBasic(Customizer.withDefaults()); // Disable httpBasic if not needed, or move it to only apply after the permitAll paths

        return http.build();
    }



    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        auth.userDetailsService(userDetailsService);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
