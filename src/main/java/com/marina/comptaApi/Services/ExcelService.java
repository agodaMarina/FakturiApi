package com.marina.comptaApi.Services;

import com.marina.comptaApi.Models.Compte;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ExcelService {

    DataFormatter formatter = new DataFormatter(Locale.FRANCE);
    public List<Compte> save(String filePath) throws IOException {
        List<Compte> listeComptes = new ArrayList<>();
        Compte compte=new Compte();
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
                compte.setId((int) row.getRowNum());
                compte.setCode(formatter.formatCellValue(codeCell));
                compte.setDescription(formatter.formatCellValue(descriptionCell));
                listeComptes.add(compte);

            }
        }
        return listeComptes;
    }
}

