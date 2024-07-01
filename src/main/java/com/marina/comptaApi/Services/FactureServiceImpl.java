package com.marina.comptaApi.Services;

import com.marina.comptaApi.Models.Facture;
import com.marina.comptaApi.Models.User;
import com.marina.comptaApi.Repositories.FactureRepository;
import lombok.Data;
import net.sourceforge.tess4j.Tesseract;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Data
public class FactureServiceImpl implements FactureService {
    private final FactureRepository factureRepository;

    private final Tesseract tesseract;
    public static final String BASEURL="C:\\Users\\AGODA Marina\\Documents\\IPNET INSTITUTE OF TECHNOLOGY\\STAGE\\TRUSTLINE\\Projet Comptabilité\\captures";


//    File file = new File("info.txt");
//    FileWriter fileWriter = new FileWriter(file, false);
//    private BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//
//    public FactureServiceImpl(FactureRepository factureRepository, Tesseract tesseract) throws IOException {
//        this.factureRepository = factureRepository;
//        this.tesseract = tesseract;
//    }
//
//
//    @Override
//    public String getImageString(MultipartFile multipartFile) throws TesseractException, IOException {
//        final String orginalFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//        Path filePath = Paths.get(BASEURL+"\\"+orginalFileName);
//        final String orcToString = tesseract.doOCR(new File(String.valueOf(filePath)));
//        bufferedWriter.write(orcToString);
//
//        return orcToString;
//    }


    /*
    public String extractTextFromImage(MultipartFile file) {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("/usr/share/tesseract-ocr/4.00/tessdata"); // Mettez le chemin vers vos données Tesseract
        try {
            return tesseract.doOCR(convert(file));
        } catch (TesseractException | IOException e) {
            e.printStackTrace();
            return "Error during OCR";
        }
    }

    private File convert(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        file.transferTo(convFile);
        return convFile;
    }*/

    /*public Facture traiterFacture(Facture facture) {
        // Logique de correspondance des comptes
        Compte compte = determinerCompte(facture);
        facture.setCompte(compte);

        return factureRepository.save(facture);
    }*/

    @Override
    public void addFacture(Facture facture) {
        User user1=new User();
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            Object principal = auth.getPrincipal();
            if (principal instanceof User) {
                user1= (User) principal;
            } else {
                throw new IllegalStateException("Type de principal non supporté : " + principal.getClass().getName());
            }
        }
        Facture facture1 = Facture.builder()
                .date(LocalDateTime.now())
                .tva(facture.getTva())
                .numero(facture.getNumero())
                .totaltva(facture.getTotaltva())
                .totalttc(facture.getTotalttc())
                .user(user1)
                .build();
        factureRepository.save(facture1);
    }

    @Override
    public void deleteFacture(Long id) {
        factureRepository.deleteById(id);
    }

    @Override
    public Facture getFacture() {
        return null;
    }

    @Override
    public List<Facture> getAllFactures() {
        return factureRepository.findAll();
    }

    @Override
    public ByteArrayInputStream exportFacturesToExcel() throws IOException {
        String[] columns = {"Numero","Date", "tva", "Total Tva", "Total TTC"};

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Factures");

            // Header row
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < columns.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(columns[col]);
            }

            // Data rows
            List<Facture> factures = getAllFactures();
            int rowIdx = 1;
            for (Facture facture : factures) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(facture.getNumero());
                row.createCell(1).setCellValue(facture.getDate().toString());
                row.createCell(2).setCellValue(facture.getTva());
                row.createCell(3).setCellValue(facture.getTotaltva());
                row.createCell(4).setCellValue(facture.getTotalttc());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    @Override
    public void getFactureByProprietaire() {

    }
}
