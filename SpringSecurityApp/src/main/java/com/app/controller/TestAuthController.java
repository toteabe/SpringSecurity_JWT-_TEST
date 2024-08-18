package com.app.controller;


import com.app.config.exception.SecurityErrorHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authorization.method.HandleAuthorizationDenied;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/method")
//@HandleAuthorizationDenied(handlerClass = SecurityErrorHandler.class)
public class TestAuthController {

    @GetMapping("/get")
    public String helloGet() {
        return "Hello World - GET";
    }


    @PostMapping("/post")
    public String helloPost() {
        return "Hello World - POST";
    }


    @PutMapping("/put")
    public String helloPut() {
        return "Hello World - PUT";
    }


    @DeleteMapping("/delete")
    public String helloDelete() {
        return "Hello World - DELETE";
    }


    @PatchMapping("/patch")
    public String helloPatch() {
        return "Hello World - PATCH";
    }
}
