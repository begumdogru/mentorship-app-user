package com.mentorship.user_service.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @PostMapping("/register")
    public String registerUser(String request) {
        System.out.println("Register request received: " + request);
        return request;
    
}
}
