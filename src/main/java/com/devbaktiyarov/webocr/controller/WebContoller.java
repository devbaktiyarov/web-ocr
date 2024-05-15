package com.devbaktiyarov.webocr.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.devbaktiyarov.webocr.service.PdfConvertService;
import com.devbaktiyarov.webocr.service.TessOcrService;
import com.devbaktiyarov.webocr.service.WordConverService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/")
public class WebContoller {

    private final TessOcrService textConvertService;
    private final PdfConvertService pdfConvertService;
    private final WordConverService wordConvertService;

    private String wordContentype = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    private String dateFormat = "yyyy-MM-dd:hh:mm:ss";
    private String headerKey = "Content-Disposition";

    public WebContoller(TessOcrService textConvertService, PdfConvertService pdfConvertService,
            WordConverService wordConvertService) {
        this.textConvertService = textConvertService;
        this.pdfConvertService = pdfConvertService;
        this.wordConvertService = wordConvertService;
    }

    @GetMapping()
    public String getMainPage() {
        return "main";
    }

    @GetMapping("/pick")
    public String getPickPage() {
        return "index";
    }

    @PostMapping(path = "/convert", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    private String convertImage(@RequestParam MultipartFile[] files, 
            @RequestParam String lang, Model model) {      
        model.addAttribute("images", textConvertService.converImageToText(files, lang));
        return "result";
    }

    @GetMapping("/pdf-converter")
    public String getPdfPage() {
        return "pdf-converter";
    }
    

    @PostMapping("/convert-to-pdf")
    public void convertToPdf(HttpServletResponse response, 
            @RequestParam MultipartFile[] files, 
            @RequestParam String lang) {
                
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerValue = "attachment; filename=pdf_ars" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        pdfConvertService.converImageToPdf(response, files, lang);  
    }


    @GetMapping("/word-converter")
    public String getWordPage() {
        return "word-converter";
    }

    @PostMapping("/convert-to-word")
    public void convertToWord(HttpServletResponse response, 
            @RequestParam MultipartFile[] files, 
            @RequestParam String lang ) {

        response.setContentType(wordContentype);
        DateFormat dateFormatter = new SimpleDateFormat(dateFormat);
        String currentDateTime = dateFormatter.format(new Date());
        String headerValue = "attachment; filename=web-ocr" + currentDateTime + ".docx";
        response.setHeader(headerKey, headerValue);
        wordConvertService.converImageToWord(response, files, lang);  
    }

}
    