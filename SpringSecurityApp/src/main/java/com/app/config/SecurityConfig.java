package com.app.config;

import com.app.config.filter.JwtTokenValidator;
import com.app.service.UserDetailServiceImpl;
import com.app.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static jakarta.servlet.DispatcherType.ERROR;
import static jakarta.servlet.DispatcherType.FORWARD;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtils JwtUtils;
    /*
     * Representamos la configuración de seguridad de la aplicación
     *
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

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity/*, AuthenticationProvider authenticationProvider*/) throws Exception {
        return httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {

                    //Ver https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html
                    http.dispatcherTypeMatchers(FORWARD, ERROR).permitAll()
                            //PARA LAS PETICIONES preflight OPTIONS DEL NAVEGADOR :p
                            //Ver https://stackoverflow.com/questions/76682586/allow-cors-with-spring-security-6-1-1-with-authenticated-requests

                            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                            .requestMatchers("/auth-cookie/**").permitAll()

                    // EndPoints publicos
//                    .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
//                    .requestMatchers(HttpMethod.GET, "/personas").permitAll()

                    // EndPoints Privados
                    .requestMatchers(HttpMethod.GET, "/personas/nuevo").hasAnyRole("ADMIN", "DEVELOPER", "USER")

                    .requestMatchers(HttpMethod.POST, "/personas/save").hasAnyRole("ADMIN", "DEVELOPER", "USER")
//                    http.requestMatchers(HttpMethod.GET, "/personas/eliminar/").hasAnyRole("ADMIN", "DEVELOPER");
                    .requestMatchers(HttpMethod.GET, "/personas/eliminar/").hasAnyRole("INVITED")

                    .requestMatchers(HttpMethod.GET, "/personas/actualizar/").hasAuthority("UPDATE")

                    .anyRequest().authenticated();
                })
                .addFilterBefore(new JwtTokenValidator(JwtUtils), BasicAuthenticationFilter.class)
                .build();
    }

    /*Asiganando permisos con Anotaciones @PreAuthorize  directo en el Controller
     * recordar que para ello debe estar habilitado el @EnableMethodSecurity
     * */

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf(csrf -> csrf.disable())
//                .httpBasic(Customizer.withDefaults())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(new JwtTokenValidator(JwtUtils), BasicAuthenticationFilter.class)
//                .build();
//    }

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

    /* PrePostTemplateDefaults
     *  Esta clase es un Bean de Spring que se encarga de configurar los permisos por defecto y
     * personalizados como el roleNeed que se creo en el paquete config
     * El bean se encarga de configurar los permisos por defecto y personalizados
     * desactivando los permisos por defecto y activando los permisos personalizados
     * en caso de  usar los predeterminados de Spring se debe desactivar los personalizados
     * incluyendo el bean en la clase SecurityConfig (PrePostTemplateDefaults)
     *  */

//    @Bean
//    public PrePostTemplateDefaults prePostTemplateDefaults() {
//        return new PrePostTemplateDefaults();
//    }


//    public static void main(String[] args) {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String password = "1234";
//        String passwordEncoded = bCryptPasswordEncoder.encode(password);
//        System.out.println(passwordEncoded);
//    }
}
