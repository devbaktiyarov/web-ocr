package com.devbaktiyarov.webocr.service;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.devbaktiyarov.webocr.model.Image;
import com.lowagie.text.Document;

import jakarta.servlet.http.HttpServletResponse;

public interface TessOcrService {
    ArrayList<Image> converImageToText(MultipartFile[] files, String language);
    void converImageToPdf(HttpServletResponse response, MultipartFile[] files, String language);
}
