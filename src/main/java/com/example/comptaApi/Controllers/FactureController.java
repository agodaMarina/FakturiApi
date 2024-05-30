package com.example.comptaApi.Controllers;

import com.example.comptaApi.Services.FactureService;
import lombok.AllArgsConstructor;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/facture")
@AllArgsConstructor
public class FactureController {

    private final FactureService factureService;

    @GetMapping("/addImage")
    public ResponseEntity<String> getImageToString(@RequestParam MultipartFile multipartFile) throws TesseractException, IOException {
        return new ResponseEntity<>(factureService.getImageString(multipartFile), HttpStatus.OK);
    }

}
