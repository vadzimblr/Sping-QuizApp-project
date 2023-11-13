package com.example.QuizzApp.configs;

import com.example.QuizzApp.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.csrf.CsrfAuthenticationStrategy;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        return http.authorizeHttpRequests(
                (authorize)->authorize.requestMatchers("/register").permitAll()
                        .anyRequest().authenticated())
                .csrf((csrf)->csrf.csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler())
                        .csrfTokenRepository(httpSessionCsrfTokenRepository)
                        .sessionAuthenticationStrategy(new CsrfAuthenticationStrategy(httpSessionCsrfTokenRepository))
                )
                .formLogin(Customizer.withDefaults())
                .build();
    }
    @Bean
    public AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver(){
        return new AuthenticationPrincipalArgumentResolver();
    }
    @Bean
    public List<AuthenticationProvider> providerList(){
        List<AuthenticationProvider> providers = new ArrayList<>();
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        providers.add(daoAuthenticationProvider);
        return providers;
    }
    @Bean
    public AuthenticationManager authenticationManager(){
        return new ProviderManager(providerList());
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
