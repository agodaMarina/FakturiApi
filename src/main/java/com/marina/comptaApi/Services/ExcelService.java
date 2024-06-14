package com.marina.comptaApi.Services;

import com.marina.comptaApi.Models.Compte;
import com.marina.comptaApi.Repositories.CompteRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

@Service
public class ExcelService {

    @Autowired
    private CompteRepository compteRepository;
    DataFormatter formatter = new DataFormatter(Locale.FRANCE);
    public void save(String filePath) throws IOException {
        try (InputStream is = new FileInputStream(filePath)) {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // sauter la première ligne qui contient les entêtes
                    continue;
                }
                Cell codeCell = row.getCell(0);
                Cell descriptionCell = row.getCell(1);

                if (codeCell == null || descriptionCell == null) {
                    continue; // sauter les lignes vides
                }

//                String code=formatter.formatCellValue(codeCell);
//                String description=formatter.formatCellValue(descriptionCell);
//                System.out.println(code);
//                System.out.println(description);
                Compte compte = new Compte();
                compte.setCode(formatter.formatCellValue(codeCell));
                compte.setDescription(formatter.formatCellValue(descriptionCell));

                //System.out.println(compte.getCode());
//                compteRepository.save(compte);
            }
        }
    }
}

