package com.devbaktiyarov.webocr.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.devbaktiyarov.webocr.model.Image;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

@Service
public class TessOcrServiceImpl implements TessOcrService {

    @Value("${tesseract.datapath}")
    private String datapath;

    public static final String FONT = "src/main/resources/static/fonts/Helvetica.ttf";

    @Override
    public ArrayList<Image> converImageToText(MultipartFile[] files, String language) {

        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath(datapath);
        tesseract.setLanguage(language);

        ArrayList<Image> images = new ArrayList<>();
        for (MultipartFile multipartFile : files) {
            try {
                String result = tesseract.doOCR(createImageFromBytes(multipartFile.getBytes()));
                String photo = "data:image/jpg;base64," + Base64.getEncoder().encodeToString(multipartFile.getBytes());
                Image img = new Image(multipartFile.getOriginalFilename(), result, photo);
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

    @Override
    public void converImageToPdf(HttpServletResponse response,
            MultipartFile[] files,
            String language) {

        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath(datapath);
        tesseract.setLanguage(language);

        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }

        document.open();

        Font fontParagraph = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, true);
        fontParagraph.setSize(10);
        for (MultipartFile file : files) {
            try {
                String result = tesseract.doOCR(createImageFromBytes(file.getBytes()));
                Paragraph paragraph = new Paragraph(result, fontParagraph);
                paragraph.setAlignment(Paragraph.ALIGN_CENTER);
                com.lowagie.text.Image image = com.lowagie.text.Image.getInstance(file.getBytes());
                image.scaleAbsolute(250, 100);
                image.setAlignment(com.lowagie.text.Image.ALIGN_CENTER);
                document.add(image);
                document.add(paragraph);
            } catch (TesseractException | IOException e) {
                e.printStackTrace();
            }
        }
        document.close();
    }

}
