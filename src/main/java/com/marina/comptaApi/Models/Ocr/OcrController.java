package com.marina.comptaApi.Models.Ocr;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/ocr")
public class OcrController {

    @Autowired
    private OcrSpaceService ocrSpaceService;

    @PostMapping("/extract-text")
    public ResponseEntity<String> extractText(@RequestParam("image") MultipartFile imageFile) {
        try {
            // 1. Vérifiez si un fichier a été envoyé
            if (imageFile.isEmpty()) {
                return ResponseEntity.badRequest().body("Veuillez sélectionner une image.");
            }

            // 3. Appelez le service OCR
            String extractedText = ocrSpaceService.extractTextFromImage(imageFile);

            // 4. Retournez le texte extrait avec un code 200 OK
            return ResponseEntity.ok(extractedText);

        } catch (RuntimeException e) {
            // 5.  En cas d'erreur (ex: erreur OCR.space, problème d'image, etc.), retournez un code 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur OCR : " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la lecture de l'image.");
        }
    }
}
