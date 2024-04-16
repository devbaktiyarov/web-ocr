package com.devbaktiyarov.webocr.controller;


import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/v1")
public class TessOcrController {
    
    
    private final Tesseract tesseract;


    public TessOcrController(Tesseract tesseract) {
        this.tesseract = tesseract;
    }




    @PostMapping("/")
    private String convertImage(@RequestParam(name = "image") MultipartFile file) throws TesseractException, IOException {
        String result = tesseract.doOCR(createImageFromBytes(file.getBytes())); // анализ текста и превращение его в данные
        System.out.println(result);
        return "";
    }



    private BufferedImage createImageFromBytes(byte[] imageData) {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        try {
            return ImageIO.read(bais);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Boolean extensionCheck(MultipartFile file) {
        String fileExtention = FilenameUtils.getExtension(file.getOriginalFilename());
        return true;
    }    
}
