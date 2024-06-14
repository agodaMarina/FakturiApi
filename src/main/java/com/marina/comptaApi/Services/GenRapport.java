package com.marina.comptaApi.Services;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.marina.comptaApi.Models.Facture;
import com.marina.comptaApi.Models.Rapport;
import com.marina.comptaApi.Repositories.FactureRepository;
import com.marina.comptaApi.Repositories.RapportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class GenRapport {

    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private RapportRepository rapportRepository;

    public byte[] generatePdf(LocalDate startDate, LocalDate endDate, String username) throws IOException {
        List<Facture> factures = factureRepository.findByDateBetween(startDate.toString(), endDate.toString());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Add Title
        document.add(new Paragraph("Rapport de Factures"));

        // Add User Name
        if (username != null) {
            document.add(new Paragraph("Utilisateur: " + username));
        }

        // Add Table
        float[] pointColumnWidths = {150F, 150F, 150F};
        Table table = new Table(pointColumnWidths);

        table.addCell(new Cell().add(new Paragraph("Numéro de Facture")));
        table.addCell(new Cell().add(new Paragraph("Date de Facture")));
        table.addCell(new Cell().add(new Paragraph("Montant de Facture")));

        double totalAmount = 0;
        for (Facture facture : factures) {
            table.addCell(new Cell().add(new Paragraph(facture.getNumero())));
            table.addCell(new Cell().add(new Paragraph(facture.getDate())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(facture.getTotalttc()))));totalAmount += facture.getTotalttc();
        }

        // Add Total Amount
        document.add(table);
        document.add(new Paragraph("Montant Total: " + totalAmount));

        document.close();
        return baos.toByteArray();
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void generateMonthlyReport() throws IOException {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = LocalDate.now();
        byte[] pdfData = generatePdf(startDate, endDate, null);
        // Save or send the PDF data as needed
    }
}

