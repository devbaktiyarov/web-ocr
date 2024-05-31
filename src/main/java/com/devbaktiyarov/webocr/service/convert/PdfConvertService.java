package com.devbaktiyarov.webocr.service.convert;

import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;

public interface PdfConvertService {
    void converImageToPdf(HttpServletResponse response, MultipartFile[] files, String language);
}


