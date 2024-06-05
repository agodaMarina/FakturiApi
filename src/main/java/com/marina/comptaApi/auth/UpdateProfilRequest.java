package com.marina.comptaApi.auth;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateProfilRequest {

    @NotBlank(message = "veuillez entrer votre prénom")
    @NotEmpty(message = "veuillez entrer votre prénom ")
    private String firstName;

    @NotBlank(message = "veuillez entrer votre nom")
    @NotEmpty(message = "veuillez entrer votre nom ")
    private String lastName;

    @NotBlank(message = "veuillez entrer un email valide--->test@example.com")
    @NotEmpty(message = "veuillez entrer un email valide--->test@example.com")
    private String email;
}
