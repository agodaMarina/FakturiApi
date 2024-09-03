package com.marina.comptaApi.Models.Ocr;

import lombok.Data;

@Data
public class OcrSpaceRequest {
    private String language;  // Optionnel : Code de langue (ex: "eng", "fra", etc.)
    private String base64Image;  // L'image encodée en base64
}
