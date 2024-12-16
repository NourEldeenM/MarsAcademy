package com.academy.mars.Security.Config;

import com.academy.mars.UserManagement.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userDetailsService;

    public SecurityConfig(UserService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // Disable CSRF for simplicity (not recommended for production)
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/auth/signup", "/auth/login").permitAll() // Allow unauthenticated access
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/instructor/**").hasRole("INSTRUCTOR")
//                        .requestMatchers("/student/**").hasRole("STUDENT")
                                .requestMatchers("/admin/**").permitAll()
                                .requestMatchers("/instructor/**").permitAll()
                                .requestMatchers("/student/**").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin().disable(); // Disable the default login page

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
