package com.devbaktiyarov.webocr.service.convert;

import java.security.Principal;

import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;

public interface WordConverService {
    void converImageToWord(HttpServletResponse response, MultipartFile[] files, String language, Principal principal);
}
