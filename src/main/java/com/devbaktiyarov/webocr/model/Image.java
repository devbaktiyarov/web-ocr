package com.devbaktiyarov.webocr.model;

import org.springframework.web.multipart.MultipartFile;

public class Image {
    private MultipartFile[] images;


    

    public Image(MultipartFile[] images) {
        this.images = images;
    }




    public MultipartFile[] getImages() {
        return images;
    }




    public void setImages(MultipartFile[] images) {
        this.images = images;
    }




    public Image() {
    }

  
  }
