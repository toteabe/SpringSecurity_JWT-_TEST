package com.app.config;

import com.app.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    /*
    *
    * Representamos la configuración de seguridad de la aplicación
    * Siguiedo el principio de seguridad por roles, se configura la seguridad de la aplicación
    * Primero el SecurityFilterChain, que es el encargado de configurar la seguridad de la aplicación
    * que debe ser un Bean de Spring,  esta delega  al AuthenticationManager
    * el AuthenticationManager delega en el AuthenticationProvider
    * el AuthenticationProvider delega en UserDetailsService y PasswordEncoder.
    *
    * de ahi UserDetailsService que es un servicio que implementa la interfaz UserDetailsService
    * busca en la base de datos el  usuario y sus roles y permisos
    * y el PasswordEncoder para comparar la contraseña encriptada
    * usamos listas de SimpleGrantedAuthority para cargar los roles y permisos
    * El SimpleGrantedAuthority es un objeto que representa un rol o permiso
    * y el User es un objeto que representa un usuario
    *
    * */

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf(csrf -> csrf.disable())
//                .httpBasic(Customizer.withDefaults())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(http -> {
//                    // Configurar los endpoints publicos
//                    http.requestMatchers(HttpMethod.GET, "/auth/get").permitAll();
//
//                    // Configurar los endpoints privados
//                    http.requestMatchers(HttpMethod.POST, "/auth/post").hasAnyRole("ADMIN", "DEVELOPER");
//                    http.requestMatchers(HttpMethod.PATCH, "/auth/patch").hasAnyAuthority("REFACTOR");
//
//                    // Configurar el resto de endpoint - NO ESPECIFICADOS
//                    http.anyRequest().denyAll();
//                })
//                .build();
//    }

/*Asiganando permisis con Anotaciones @PreAuthorize  directo en el Controller
* recordar que para ello debe estar habilitado el @EnableMethodSecurity
* */

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

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    public static void main(String[] args) {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String password = "1234";
//        String passwordEncoded = bCryptPasswordEncoder.encode(password);
//        System.out.println(passwordEncoded);
//    }
}
