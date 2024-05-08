package com.devbaktiyarov.webocr.model;

public class Image {
    private String name;
    private String text;
    private String photo;
    
    public Image(String name, String text, String photo) {
        this.name = name;
        this.text = text;
        this.photo = photo;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    

}
