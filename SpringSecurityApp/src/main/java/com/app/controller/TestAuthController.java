package com.app.controller;


import com.app.config.exception.SecurityErrorHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authorization.method.HandleAuthorizationDenied;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@HandleAuthorizationDenied(handlerClass = SecurityErrorHandler.class)
public class TestAuthController {

    @GetMapping("/get")
    @PreAuthorize("hasRole(permitAll())")
    public String helloGet() {
        return "Hello World - GET";
    }

    @PostMapping("/post")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String helloPost() {
        return "Hello World - POST";
    }

    @PutMapping("/put")
    @PreAuthorize("hasRole('ADMIN')")
    public String helloPut() {
        return "Hello World - PUT";
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String helloDelete() {
        return "Hello World - DELETE";
    }

    @PatchMapping("/patch")
    @PreAuthorize("hasRole('REFACTOR')")
    public String helloPatch() {
        return "Hello World - PATCH";
    }
}
