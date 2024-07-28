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
    @PreAuthorize("permitAll()")
    public String helloGet() {
        return "Hello World - GET";
    }

    @PostMapping("/post")
    @PreAuthorize("hasAnyRole('ADMIN','DEVELOPER','USER')")
    public String helloPost() {
        return "Hello World - POST";
    }

    @PutMapping("/put")
    @PreAuthorize("hasAnyRole('ADMIN','DEVELOPER','USER')")
    public String helloPut() {
        return "Hello World - PUT";
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('ADMIN','DEVELOPER','USER')")
    public String helloDelete() {
        return "Hello World - DELETE";
    }
   @PreAuthorize("hasAnyRole('DEVELOPER')")
    @PatchMapping("/patch")
    public String helloPatch() {
        return "Hello World - PATCH";
    }
}
