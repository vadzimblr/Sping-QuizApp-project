package com.example.QuizzApp.configs;

import com.example.QuizzApp.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CsrfAuthenticationStrategy;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {
    private final DataSource dataSource;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(DataSource dataSource, CustomUserDetailsService customUserDetailsService) {
        this.dataSource = dataSource;
        this.customUserDetailsService = customUserDetailsService;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        return http.authorizeHttpRequests(
                (authorize)->authorize
                        .requestMatchers("/register").permitAll()
                        .anyRequest().authenticated()

                        )
                .csrf((csrf)->csrf.csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler())
                        .csrfTokenRepository(httpSessionCsrfTokenRepository)
                        .sessionAuthenticationStrategy(new CsrfAuthenticationStrategy(httpSessionCsrfTokenRepository))
                )
                .formLogin((login)->login.loginPage("/login")
                        .loginProcessingUrl("/login-user")
                        .usernameParameter("user")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/")
                        .permitAll())
                .rememberMe((login)->login.key("myKey")
                        .rememberMeServices(rememberMeServices())
                        .tokenRepository(persistentTokenRepository())
                        .rememberMeParameter("remember-me")
                )
                .build();
    }
    @Bean
    public AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver(){
        return new AuthenticationPrincipalArgumentResolver();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }
    @Bean
    public RememberMeAuthenticationProvider rememberMeAuthenticationProvider(){
        return new RememberMeAuthenticationProvider("myKey");
    }
    @Bean
    public RememberMeServices rememberMeServices(){
        return new PersistentTokenBasedRememberMeServices("myKey",
                customUserDetailsService,persistentTokenRepository());
    }
    @Bean
    public List<AuthenticationProvider> providerList(){
        List<AuthenticationProvider> providers = new ArrayList<>();
        providers.add(daoAuthenticationProvider());
        providers.add(rememberMeAuthenticationProvider());
        return providers;
    }
    @Bean
    public SecurityContextRepository securityContextRepository(){
        return new HttpSessionSecurityContextRepository();
    }
    @Bean
    public AuthenticationManager authenticationManager(){
        return new ProviderManager(providerList());
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
}
