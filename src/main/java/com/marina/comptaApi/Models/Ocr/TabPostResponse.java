package com.marina.comptaApi.Models.Ocr;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TabPostResponse {
    private String message;
    private String status;
    @JsonProperty("status_code")
    private int statusCode;
    private String token;
    private boolean success;
    private int code;
    private boolean duplicate;
    @JsonProperty("duplicateToken")
    private String duplicateToken;
}
