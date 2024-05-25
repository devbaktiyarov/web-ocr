package com.devbaktiyarov.webocr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class SecurityContoller {
    
    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }
    

}
