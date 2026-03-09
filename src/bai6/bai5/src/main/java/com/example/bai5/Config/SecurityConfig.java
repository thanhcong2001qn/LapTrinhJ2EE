package com.example.bai5.Config;

import com.example.bai5.Service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        // GET /products - USER và ADMIN có thể xem danh sách
                        .requestMatchers(HttpMethod.GET, "/products").hasAnyRole("USER", "ADMIN")

                        // POST /products - Chỉ ADMIN có thể tạo mới
                        .requestMatchers(HttpMethod.POST, "/products/**").hasRole("ADMIN")

                        // PUT /products/{id} - Chỉ ADMIN có thể cập nhật
                        .requestMatchers(HttpMethod.POST, "/products/**").hasRole("ADMIN")

                        // DELETE /products/{id} - Chỉ ADMIN có thể xóa
                        .requestMatchers(HttpMethod.GET, "/products/delete/**").hasRole("ADMIN")

                        // H2 Console (nếu dùng)
                        .requestMatchers("/h2-console/**").permitAll()

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable()) // Cho phép H2 Console
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}