package com.example.comptaApi;

import net.sourceforge.tess4j.Tesseract;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ComptaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComptaApiApplication.class, args);
	}

	@Bean
	public Tesseract tesseract() {
		Tesseract tesseract = new Tesseract();
		tesseract.setLanguage("eng");
		tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");
		return tesseract;
	}


}
