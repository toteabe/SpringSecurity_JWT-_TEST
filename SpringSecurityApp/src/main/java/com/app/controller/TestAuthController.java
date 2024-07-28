package com.app.controller;


import com.app.config.exception.SecurityErrorHandler;
import com.app.config.annotation.roleNeed;
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
    //@roleNeed({"USER", "ADMIN"})
    //Yo no wrappearía la anotación @PreAuthorize en otra anotacion, java es verbosidad..
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String helloPost() {
        return "Hello World - POST";
    }

    @PutMapping("/put")
    @roleNeed({"ADMIN"})
    public String helloPut() {
        return "Hello World - PUT";
    }

    @DeleteMapping("/delete")
    @roleNeed({"ADMIN"})
    public String helloDelete() {
        return "Hello World - DELETE";
    }

    @PatchMapping("/patch")
    @roleNeed({"DEVELOPER"})
    public String helloPatch() {
        return "Hello World - PATCH";
    }
}
