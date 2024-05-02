package com.example.auctions.config;

import com.example.auctions.security.JwtAuthenticationEntryPoint;
import com.example.auctions.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SpringSecurityConfig {

    private UserDetailsService userDetailsService;

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    // Configure a password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configure security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable())
                .authorizeRequests((authorize) -> {
                    authorize.requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll();
                    authorize.requestMatchers(HttpMethod.OPTIONS, "**").permitAll();
                    authorize.requestMatchers(HttpMethod.GET, "**").permitAll();
                    authorize.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults());

        http.exceptionHandling((exception) -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // IN MEMORY AUTHENTICATION
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user1 = User.builder()
//            .username("user")
//            .password(passwordEncoder().encode("password"))
//            .roles("USER")
//            .build();
//
//        UserDetails user2 = User.builder()
//            .username("admin")
//            .password(passwordEncoder().encode("password"))
//            .roles("ADMIN")
//            .build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
//    }

    // DATABASE AUTHENTICATION

}
