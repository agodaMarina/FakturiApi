package com.example.comptaApi;

import com.example.comptaApi.Services.ExcelService;
import lombok.Data;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
@Data
public class ComptaApiApplication {


	private static ExcelService excelService;



    public static void main(String[] args) throws IOException {
		String filePath = "comptes.xlsx"; // Remplacez par le chemin réel de votre fichier
		excelService.save(filePath);
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
