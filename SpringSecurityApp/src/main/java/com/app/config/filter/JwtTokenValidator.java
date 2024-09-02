package com.app.config.filter;

import com.app.util.JwtUtils;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public class JwtTokenValidator extends OncePerRequestFilter {

    public static final String COOKIE_NAME = "my-auth-cookie";

    private JwtUtils jwtUtils;

    public JwtTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

//    @Override
//    protected void doFilterInternal(@NonNull HttpServletRequest request,
//                                    @NonNull HttpServletResponse response,
//                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
//
//        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
//
//        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
//            jwtToken = jwtToken.substring(7);
//
//            DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);
//
//            String username = jwtUtils.extractUsername(decodedJWT);
//            String stringAuthorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();
//
//            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);
//
//            SecurityContext context = SecurityContextHolder.createEmptyContext();
//            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
//            context.setAuthentication(authenticationToken);
//            SecurityContextHolder.setContext(context);
//
//        }
//        filterChain.doFilter(request, response);
//    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        Optional<Cookie> cookieAuth = Stream.of(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                .filter(cookie -> COOKIE_NAME.equals(cookie.getName()))
                .findFirst();

        if (cookieAuth.isPresent()) {
            String jwtToken = cookieAuth.get().getValue();

            DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);

            String username = jwtUtils.extractUsername(decodedJWT);
            String stringAuthorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();

            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);

            //NOTA PROF. HABRIA QUE MEJORAR ESTO CON EL AuthenticationProvider
            //NO ES NECESARIO
            //SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }

}