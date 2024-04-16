package com.devbaktiyarov.webocr.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.devbaktiyarov.webocr.model.Image;
import com.devbaktiyarov.webocr.service.TessOcrService;

import net.sourceforge.tess4j.TesseractException;

import org.springframework.web.bind.annotation.PostMapping;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class WebContoller {

    private final TessOcrService convertService;
    

    public WebContoller(TessOcrService convertService) {
        this.convertService = convertService;
    }

    @GetMapping()
    public String getMethodName(Model model) {
        model.addAttribute("file", new Image());
        return "page";
    }

    @PostMapping(path = "/convert", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    private String convertImage(@RequestParam("file") MultipartFile[] files, Model model) throws TesseractException, IOException {
        model.addAttribute("result", convertService.converImageToText(files));
        model.addAttribute("image", "");
        return "result";
    }
    
}

