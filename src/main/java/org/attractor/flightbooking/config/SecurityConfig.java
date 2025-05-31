package org.attractor.flightbooking.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/flights", true)
                        .failureUrl("/auth/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .permitAll())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/company/**").hasAuthority("COMPANY")
                        .requestMatchers("/profile/**").hasAuthority("USER")
                        .anyRequest().permitAll());
        return http.build();
    }

}