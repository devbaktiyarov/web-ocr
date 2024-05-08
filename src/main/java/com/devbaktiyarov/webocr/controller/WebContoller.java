package com.devbaktiyarov.webocr.controller;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.devbaktiyarov.webocr.service.TessOcrService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/")
public class WebContoller {

    private final TessOcrService convertService;

    public WebContoller(TessOcrService convertService) {
        this.convertService = convertService;
    }

    @GetMapping()
    public String getMainPage() {
        return "main";
    }

    @PostMapping(path = "/convert", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    private String convertImage(@RequestParam MultipartFile[] files, 
            @RequestParam String lang, Model model) {      
        model.addAttribute("images", convertService.converImageToText(files, lang));
        return "result";
    }

}
