package com.devbaktiyarov.webocr.model;

import org.springframework.web.multipart.MultipartFile;

public class Image {
    private String name;
    private String text;
    
    public Image(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    
    
  
  }
