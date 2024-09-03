package com.marina.comptaApi;

import com.marina.comptaApi.Models.dona.Client;
import com.marina.comptaApi.Repositories.test.ClientRepository;
import com.marina.comptaApi.Repositories.test.ClientService;
import com.marina.comptaApi.Services.ExcelService;

import net.sourceforge.tess4j.Tesseract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

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

