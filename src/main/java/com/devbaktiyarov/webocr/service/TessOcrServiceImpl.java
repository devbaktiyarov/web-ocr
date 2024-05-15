package com.devbaktiyarov.webocr.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.devbaktiyarov.webocr.model.Image;
import com.devbaktiyarov.webocr.util.TessUtil;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

@Service
public class TessOcrServiceImpl implements TessOcrService {

    private Tesseract tesseract;

    public TessOcrServiceImpl(Tesseract tesseract) {
        this.tesseract = tesseract;
    }

    @Override
    public ArrayList<Image> converImageToText(MultipartFile[] files, String language) {

        tesseract.setLanguage(language);

        ArrayList<Image> images = new ArrayList<>();
        for (MultipartFile multipartFile : files) {
            try {
                String result = tesseract.doOCR(TessUtil.createImageFromBytes(multipartFile.getBytes()));
                String photo = "data:image/jpg;base64," + Base64.getEncoder().encodeToString(multipartFile.getBytes());
                Image img = new Image(multipartFile.getOriginalFilename(), result, photo);
                images.add(img);
            } catch (TesseractException | IOException e) {
                e.printStackTrace();
            }
        }
        return images;
    }

}
