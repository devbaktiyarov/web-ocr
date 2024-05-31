package com.devbaktiyarov.webocr.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.devbaktiyarov.webocr.entity.UserProfile;
import com.devbaktiyarov.webocr.service.security.RegistrationService;


@Controller
public class SecurityContoller {
    
    private final RegistrationService registrationService;

    public SecurityContoller(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserProfile user) {
        registrationService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful");
    }
    

}
