package com.marina.comptaApi.Models.Ocr;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class OcrSpaceResponse {
    private List<ParsedResult> parsedResults;
    private int ocrExitCode;
    private String ErrorMessage;
    private String ErrorDetails;


    // Getters and setters

    @Getter
    public static class ParsedResult {
        private String parsedText;
        // Autres champs...

        // Getters and setters
    }
}
