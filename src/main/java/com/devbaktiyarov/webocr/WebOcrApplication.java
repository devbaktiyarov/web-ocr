package com.devbaktiyarov.webocr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import net.sourceforge.tess4j.Tesseract;

@SpringBootApplication
public class WebOcrApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebOcrApplication.class, args);
	}


	// @Bean
	// public Tesseract tesseract() {
	// 	Tesseract tesseract = new Tesseract();
	// 	tesseract.setDatapath("src/main/resources/tessdata");
	// 	return tesseract;
	// }

}
