package com.app.config;


import com.app.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

//SIN ANOTACIONES EN EL CONTROLADOR
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf(csrf -> csrf.disable())
//                .httpBasic(Customizer.withDefaults())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
//                authorizeHttpRequests(http -> {
//                    //configuracion de endpoint publicos
//                    http.requestMatchers(HttpMethod.GET, "auth/hello").permitAll();
//
//                    //configuracion de endpoint privados
//                    http.requestMatchers(HttpMethod.GET, "/auth/hello-secured").hasAuthority("CREATE");
//
//                    //cualquier otro endopoint - NO ESPECIFICADO
//                    http.anyRequest().denyAll();
//
//                    //denyAll();   solo perimite aquellos configurados
//                    // authenticated();  para   todo lo no configurado,  debo estar autenticado
//                })
//                .build();
//    }


    // CON ANOTACIONES EN EL CONTROLADOR  para las autorizaciones
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    // va a la base de datos y me trae usuarios
    // pasa de provider a UserDetailsService -> a Bd
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }


    // Usar el NoOpPasswordEncoder exclusivamente para prubas ya que esta deprecada
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}