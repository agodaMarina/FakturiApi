package com.marina.comptaApi;

import com.marina.comptaApi.Services.ExcelService;
import lombok.RequiredArgsConstructor;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.io.IOException;

@SpringBootApplication()
@EnableScheduling
@EnableAsync
public class ComptaApiApplication {

	public static void main(String[] args) throws IOException {

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

@Component
@RequiredArgsConstructor
class ExcelServiceRunner implements CommandLineRunner{

	private final ExcelService excelService;
	@Override
	public void run(String... args) throws Exception {
		String filePath = "comptes.xlsx"; // chemin du fichier excel
		excelService.save(filePath);

	}
}