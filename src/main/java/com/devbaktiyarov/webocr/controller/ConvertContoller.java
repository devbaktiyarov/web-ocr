package com.devbaktiyarov.webocr.controller;

import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.devbaktiyarov.webocr.service.convert.PdfConvertService;
import com.devbaktiyarov.webocr.service.convert.TessOcrService;
import com.devbaktiyarov.webocr.service.convert.WordConverService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
@PropertySource("classpath:messages.properties")
@PropertySource("classpath:messages_ru.properties")
public class ConvertContoller {


    // @Value("${tesseract.lang.names}")
    // private List<String> listOfLanguages;


    // @Value("${tesseract.lang.codes}")
    // private List<String> listOfLanguageCodes;

    // @Value("${convert}")
    // private String convert;
    

    private final TessOcrService textConvertService;
    private final PdfConvertService pdfConvertService;
    private final WordConverService wordConvertService;

  
    public ConvertContoller(TessOcrService textConvertService, PdfConvertService pdfConvertService,
            WordConverService wordConvertService) {
        this.textConvertService = textConvertService;
        this.pdfConvertService = pdfConvertService;
        this.wordConvertService = wordConvertService;
    }

    // @GetMapping("path")
    // @ResponseBody
    // public List<String> getMethodName() {
    //     return listOfLanguages;
    // }

    // @GetMapping("con")
    // @ResponseBody
    // public String getMethodNamee(HttpServletResponse response) {
    //     response.setCharacterEncoding("UTF-8");
    //     return convert;
    // }
    
    

    @GetMapping()
    public String getMainPage() {
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
    