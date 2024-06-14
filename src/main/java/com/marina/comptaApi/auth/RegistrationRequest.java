package com.marina.comptaApi.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {

    @NotBlank(message = "veuillez entrer un prénom")
    private String firstName;

    @NotBlank(message = "veuillez entrer un nom")
    private String lastName;

    @NotBlank(message = "veuillez entrer un email")
    @NotEmpty(message = "votre email doit être valide --->test@example.com")
    private String email;

    @NotBlank(message = "veuillez entrer votre numero de téléphone")
    @NotEmpty(message = "Veuillez entrer votre numéro de téléphone")
    private String telephone;

    @NotBlank(message = "veuillez entrer un mot de passe ")
    private String password;

    @NotEmpty(message = "veuillez entrer un rôle valide: administrateur ou comptable ou utilisateur")
    private String role;
}
