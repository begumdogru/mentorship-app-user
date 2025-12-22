package com.mentorship.user_service.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    
    @PostMapping("/login")
    public String login(@RequestBody String request) {
        System.out.println("Login request received: " + request);
        return request;        
    }
}
