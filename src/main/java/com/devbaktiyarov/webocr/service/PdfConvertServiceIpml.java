package com.devbaktiyarov.webocr.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.devbaktiyarov.webocr.util.TessUtil;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Service
public class PdfConvertServiceIpml implements PdfConvertService {


    private static final String FONT = "src/main/resources/static/fonts/Helvetica.ttf";
    private static String dateFormat = "yyyy-MM-dd:hh:mm:ss";
    private static String headerKey = "Content-Disposition";


    private Tesseract tesseract;
    
    public PdfConvertServiceIpml(Tesseract tesseract) {
        this.tesseract = tesseract;
    }

    @Override
    public void converImageToPdf(HttpServletResponse response,
            MultipartFile[] files,
            String language) {

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat(dateFormat);
        String currentDateTime = dateFormatter.format(new Date());
        String headerValue = "attachment; filename=web_ocr" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);


        tesseract.setLanguage(language);
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            PdfWriter.getInstance(document, new FileOutputStream("files/pdf/" + "web_ocr" + currentDateTime + ".pdf"));
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }

        document.open();

        Font fontParagraph = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, true);
        fontParagraph.setSize(10);
        for (MultipartFile file : files) {
            try {
                String result = tesseract.doOCR(TessUtil.createImageFromBytes(file.getBytes()));
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
