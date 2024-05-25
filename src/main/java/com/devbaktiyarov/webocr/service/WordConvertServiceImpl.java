package com.devbaktiyarov.webocr.service;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.devbaktiyarov.webocr.util.TessUtil;

import jakarta.servlet.http.HttpServletResponse;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;


@Service
public class WordConvertServiceImpl implements WordConverService{

    private static String wordContentype = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    private static String dateFormat = "yyyy-MM-dd:hh:mm:ss";
    private static String headerKey = "Content-Disposition";
    
    private Tesseract tesseract;

    public WordConvertServiceImpl(Tesseract tesseract) {
        this.tesseract = tesseract;
    }

    @Override
    public void converImageToWord(HttpServletResponse response, MultipartFile[] files, String language) {
        
        response.setContentType(wordContentype);
        DateFormat dateFormatter = new SimpleDateFormat(dateFormat);
        String currentDateTime = dateFormatter.format(new Date());
        String headerValue = "attachment; filename=web_ocr" + currentDateTime + ".docx";
        response.setHeader(headerKey, headerValue);
        tesseract.setLanguage(language);
        XWPFDocument document = new XWPFDocument();
            for (MultipartFile file : files) {

                XWPFParagraph fileName = document.createParagraph();
                fileName.setAlignment(ParagraphAlignment.START);

                XWPFRun fileNameRun = fileName.createRun();
                XWPFParagraph fileText = document.createParagraph();

                fileText.setAlignment(ParagraphAlignment.START);
                XWPFRun fileTextRun = fileName.createRun();
               
                XWPFParagraph image = document.createParagraph();
                XWPFRun imageRun = image.createRun();
                try {
                    fileNameRun.setText(file.getOriginalFilename());
                    fileNameRun.setBold(true);
                    fileNameRun.setFontSize(16);
                    fileNameRun.setFontFamily("Times New Roman");
                    
                    fileTextRun.setText(tesseract.doOCR(TessUtil.createImageFromBytes(file.getBytes())));
                    fileTextRun.setFontFamily("Times New Roman");
                    InputStream is = new ByteArrayInputStream(file.getBytes());
                    imageRun.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, "my pic", Units.toEMU(300), Units.toEMU(200));   
                } catch (TesseractException | IOException | InvalidFormatException e) {
                    e.printStackTrace();
                }
            }
        try {
            document.write(response.getOutputStream());
            document.write(new FileOutputStream("files/word/" + "web_ocr" + currentDateTime + ".docx"));
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}
