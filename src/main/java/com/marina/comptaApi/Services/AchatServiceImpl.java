package com.marina.comptaApi.Services;

import com.marina.comptaApi.Models.*;
import com.marina.comptaApi.Repositories.AchatRepository;
import com.marina.comptaApi.auth.AuthenticationService;
import com.marina.comptaApi.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
@RequiredArgsConstructor
public class AchatServiceImpl implements AchatService {

    private final AchatRepository achatRepository;
    private final ImageService imageService;
    private final AuthenticationService userService;
    private final SoldeService soldeService;

    @Override
    public Achat save(Achat achat, MultipartFile file) throws IOException {
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
        Achat achat1 = Achat.builder()
                .dateEmission(achat.getDateEmission())
                .dateEcheance(achat.getDateEcheance())
                .tva(achat.getTva())
                .numero(achat.getNumero())
                .totaltva(achat.getTotaltva())
                .totalttc(achat.getTotalttc())
                .user(user1)
                .image(imageService.upload(file))
                .build();
        achatRepository.save(achat1);

        Solde solde = soldeService.soldeActuel(user1.getId());
        if (solde != null) {
            solde.setSolde(solde.getSolde()-achat1.getTotalttc());
        }
        return  achat1;
    }

    @Override
    public Optional<List<Achat>> findByUser() {
        return achatRepository.findByUserId(userService.getProfile().getId());
    }

    @Override
    public Achat update(Achat achat) {
        return achatRepository.save(achat);
    }

    @Override
    public void delete(Long id) {
        achatRepository.deleteById(id);
    }

    @Override
    public Achat findById(Long id) {

        /*FileData image = achat.getImage();
        byte[] images= ImageUtils.decompressImage(image.getImageData());
        image.setImageData(images);

        achat.setImage(image);*/
        return achatRepository.findById(id).orElse(null);
    }

    @Override
    public List<Achat> findAll() {
        return achatRepository.findAll();
    }

//exporter les factures en format excel
    public ByteArrayInputStream exportFacturesToExcel() throws IOException {
        String[] columns = {"Numero","Fournisseur", "tva", "Total Tva", "Total TTC","statut","Date Emission"};

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Factures");

            // Header row
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < columns.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(columns[col]);
            }

            // Data rows
            List<Achat> factures =findAll();
           int rowIdx = 1;
            for (Achat facture : factures) {
               Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(facture.getNumero());
                row.createCell(1).setCellValue(facture.getFournisseur());
                row.createCell(2).setCellValue(facture.getTva());
                row.createCell(3).setCellValue(facture.getTotaltva());
                row.createCell(4).setCellValue(facture.getTotalttc());
                row.createCell(5).setCellValue(facture.getStatut());
                row.createCell(6).setCellValue(facture.getDateEmission().toString());
            }


            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
       }
    }

    @Override
    public List<Achat> findBetweenDates(LocalDate date1, LocalDate date2) {
        return achatRepository.findByDateEmissionBetween(date1, date2);
    }
}
