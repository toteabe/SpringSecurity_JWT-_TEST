package com.app.controller;

import com.app.config.filter.JwtTokenValidator;
import com.app.config.model.Persona;
import com.app.controller.dto.AuthCreateUserRequest;
import com.app.controller.dto.AuthLoginRequest;
import com.app.controller.dto.AuthResponse;
import com.app.service.UserDetailServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("/auth-cookie")
public class AuthenticationCookieJWTController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("userRequest", new AuthLoginRequest(null, null));
        return "login";
    }

    @PostMapping("/login")
    public String login  (@Valid AuthLoginRequest userRequest, HttpServletResponse servletResponse) {

        AuthResponse authResponse = this.userDetailService.loginUser(userRequest);

        Cookie authCookie = new Cookie(JwtTokenValidator.COOKIE_NAME, authResponse.jwt());
        authCookie.setHttpOnly(true);
        authCookie.setSecure(true);
        authCookie.setMaxAge((int) Duration.of(1, ChronoUnit.DAYS).toSeconds());
        authCookie.setPath("/");

        servletResponse.addCookie(authCookie);

        return "personas";
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register  (@Valid AuthCreateUserRequest userRequest ) {
        return new ResponseEntity<>(this.userDetailService.createUser(userRequest), HttpStatus.CREATED);
    }

}
