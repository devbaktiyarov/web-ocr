package com.devbaktiyarov.webocr.service.convert;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.devbaktiyarov.webocr.model.Image;


public interface TessOcrService {
    ArrayList<Image> converImageToText(MultipartFile[] files, String language);
}
