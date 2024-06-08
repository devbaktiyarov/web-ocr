package com.devbaktiyarov.webocr.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.devbaktiyarov.webocr.entity.UserProfile;
import com.devbaktiyarov.webocr.service.security.RegistrationService;

import jakarta.servlet.http.HttpServletRequest;



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

    @GetMapping("/sign-up")
    public String getRegistrationPage(){
        return "registration";
    }


    

    @PostMapping("/register")
    public String registerUser(@RequestParam String email, @RequestParam String password) {
        UserProfile user = new UserProfile();
        user.setEmail(email);
        user.setPassword(password);
        registrationService.register(user);
        return "redirect:/";
    }

    @GetMapping("/principal")
    @ResponseBody
    public String getMethodName(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        return principal.getName();
    }
    
    

}
