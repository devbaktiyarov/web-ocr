package com.devbaktiyarov.webocr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.devbaktiyarov.webocr.model.ImageFile;
import com.devbaktiyarov.webocr.repository.FileRepository;
import com.devbaktiyarov.webocr.service.PdfConvertService;
import com.devbaktiyarov.webocr.service.TessOcrService;
import com.devbaktiyarov.webocr.service.WordConverService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class ConvertContoller {

    private final TessOcrService textConvertService;
    private final PdfConvertService pdfConvertService;
    private final WordConverService wordConvertService;

    @Autowired
    FileRepository repo;

    public ConvertContoller(TessOcrService textConvertService, PdfConvertService pdfConvertService,
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
        repo.save(new ImageFile(1, "Hello"));
        return "main";
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
        wordConvertService.converImageToWord(response, files, lang);  
    }

}
    