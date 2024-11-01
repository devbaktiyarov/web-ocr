package com.devbaktiyarov.webocr.controller;

import java.security.Principal;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.devbaktiyarov.webocr.entity.UserProfile;
import com.devbaktiyarov.webocr.service.security.RegistrationService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class SecurityContoller {
    
    @Value("${verificationCode.bound}")
    private Integer bound;

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
    public String registerUser(@RequestParam String email, @RequestParam String password, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        boolean err = registrationService.validateUser(email);
        if (err) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid email");
            return "redirect:/sign-up";
        }
        UserProfile user = new UserProfile();
        user.setEmail(email);
        user.setPassword(password);
        user.setEnabled(false);
        SecureRandom secureRandom = new SecureRandom();
        user.setVerificationCode(String.valueOf(secureRandom.nextInt(bound)));
        registrationService.register(user, getSiteURL(request));
        return "redirect:/";
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
       if (registrationService.verify(code)) {
           return "redirect:/login";
       } else {
           return "redirect:/";
       }
    }

    @GetMapping("/principal")
    @ResponseBody
    public String getMethodName(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        return principal.getName();
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
    
    

}
