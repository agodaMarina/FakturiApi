package com.marina.comptaApi.Models.Ocr;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class OcrSpaceService {

    private final WebClient webClient;

    // Injectez WebClient.Builder via le constructeur
    public OcrSpaceService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.ocr.space/").build();
    }

    public String extractTextFromImage(MultipartFile imageFile) throws IOException {
        // 1. Détecter le type MIME de l'image
        String mimeType = imageFile.getContentType();
        if (mimeType == null) {
            throw new IllegalArgumentException("Type MIME de l'image manquant.");
        }

        // 2. Déterminer l'extension de fichier en fonction du type MIME
        String fileExtension = switch (mimeType) {
            case "image/jpeg" -> "jpg";
            case "image/png" -> "png";
            default -> throw new IllegalArgumentException("Type MIME d'image non pris en charge: " + mimeType);
        };

        // 3. Encoder l'image en base64
        String base64Image = Base64.getEncoder().encodeToString(imageFile.getBytes());

        // 4. Construire le préfixe "data:image/*" dynamiquement
        String base64ImagePrefix = "data:" + mimeType + ";base64,";

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("language", "eng");
        formData.add("base64Image", base64ImagePrefix + base64Image);
        formData.add("apikey", "K84079317688957");

        return webClient.post()
                .uri("parse/image")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(OcrSpaceResponse.class)
                .map(response -> {
                    // ... (Gestion des erreurs)
                    return response.getParsedResults().get(0).getParsedText();
                })
                .block();
    }
}
