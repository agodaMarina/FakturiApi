package com.marina.comptaApi.Models.Ocr;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("invoice")
public class InvoiceController {

    @PostMapping("/process")
    public ResponseEntity<?> processInvoice(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
        }

        try {
            // Sauvegarder le fichier temporairement
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            file.transferTo(convFile);

            // Analyse OCR
            ITesseract tesseract = new Tesseract();
            tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");  // Chemin vers les fichiers de données Tesseract
            tesseract.setLanguage("eng");

            String extractedText = tesseract.doOCR(convFile);
            InvoiceData invoiceData = extractInvoiceData(extractedText);

            return ResponseEntity.ok(invoiceData);

        } catch (IOException | TesseractException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private InvoiceData extractInvoiceData(String text) {
        InvoiceData data = new InvoiceData();

        data.setSupplier(extractSupplier(text));
        data.setDate(extractDate(text));
        data.setItems(extractItems(text));
        data.setTotal(extractTotal(text));
        data.setTax(extractTax(text));
        data.setTotalWithTax(extractTotalWithTax(text));

        return data;
    }

    private String extractSupplier(String text) {
        String[] lines = text.split("\n");
        for (String line : lines) {
            if (line.trim().matches("^[A-Z ]+$")) {
                return line.trim();
            }
        }
        return "Unknown Supplier";
    }

    private String extractDate(String text) {
        Pattern datePattern = Pattern.compile("\\b\\d{2}/\\d{2}/\\d{2,4}\\b");
        Matcher matcher = datePattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return "Unknown Date";
    }

    private List<String> extractItems(String text) {
        List<String> items = new ArrayList<>();
        String[] lines = text.split("\n");
        for (String line : lines) {
            if (line.matches(".*\\d+\\.\\d{2}.*")) {
                items.add(line.trim());
            }
        }
        return items;
    }

    private String extractTotal(String text) {
        Pattern totalPattern = Pattern.compile("(Total|Montant TTC)\\s*:\\s*(\\d+\\.\\d{2})", Pattern.CASE_INSENSITIVE);
        Matcher matcher = totalPattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(2);
        }
        return "Unknown Total";
    }

    private String extractTax(String text) {
        Pattern taxPattern = Pattern.compile("Monnaie\\s*:\\s*(\\d+\\.\\d{2})", Pattern.CASE_INSENSITIVE);
        Matcher matcher = taxPattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "Unknown Tax";
    }

    private String extractTotalWithTax(String text) {
        Pattern totalWithTaxPattern = Pattern.compile("(Espèce|Cash)\\s*:\\s*(\\d+\\.\\d{2})", Pattern.CASE_INSENSITIVE);
        Matcher matcher = totalWithTaxPattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(2);
        }
        return "Unknown Total with Tax";
    }

}

