package com.devbaktiyarov.webocr.service;

import org.springframework.web.multipart.MultipartFile;

public interface TessOcrService {
    String converImageToText(MultipartFile[] files);
}
