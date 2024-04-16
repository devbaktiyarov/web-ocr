package com.devbaktiyarov.webocr.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class TessOcrServiceImpl implements TessOcrService{
    
    private final Tesseract tesseract;

    
    public TessOcrServiceImpl(Tesseract tesseract) {
        this.tesseract = tesseract;
    }


    @Override
    public String converImageToText(MultipartFile[] files) {
        String result = "";
        for (MultipartFile multipartFile : files) {
            try {
                result += tesseract.doOCR(createImageFromBytes(multipartFile.getBytes()));
            } catch (TesseractException | IOException e) {
                e.printStackTrace();
            }
        }
        return result;
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
