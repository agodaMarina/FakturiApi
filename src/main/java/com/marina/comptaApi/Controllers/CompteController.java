package com.marina.comptaApi.Controllers;


import com.marina.comptaApi.Services.ExcelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("comptes")
public class CompteController {
    private final ExcelService excelService;

    public CompteController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @GetMapping("/getcompte")
    public void exportComptes() throws IOException {
        excelService.save("comptes.xlsx");
    }


}
