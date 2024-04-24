package com.devbaktiyarov.webocr.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.devbaktiyarov.webocr.model.Image;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class TessOcrServiceImpl implements TessOcrService{
    
    @Override
    public ArrayList<Image> converImageToText(MultipartFile[] files, String language) {
        
        Tesseract tesseract = new Tesseract();
		tesseract.setDatapath("src/main/resources/tessdata");

        ArrayList<Image> images = new ArrayList<>();
        tesseract.setLanguage(language);
        for (MultipartFile multipartFile : files) {
            try {
                String result = tesseract.doOCR(createImageFromBytes(multipartFile.getBytes()));
                Image img = new Image(multipartFile.getOriginalFilename(), result);
                images.add(img);
            } catch (TesseractException | IOException e) {
                e.printStackTrace();
            }
        }
        return images;
    }

    private BufferedImage createImageFromBytes(byte[] imageData) {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        try {
            return ImageIO.read(bais);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
