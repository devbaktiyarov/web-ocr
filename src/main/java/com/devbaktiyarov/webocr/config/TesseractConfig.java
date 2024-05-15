package com.devbaktiyarov.webocr.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sourceforge.tess4j.Tesseract;

@Configuration
public class TesseractConfig {

    @Value("${tesseract.datapath}")
    private String datapath;

    @Value("${tesseract.dpi}")
    private String dpi;

    @Bean
    public Tesseract tesseract() {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath(datapath);
        tesseract.setVariable("user_defined_dpi", dpi);
        return tesseract;
    }
}
