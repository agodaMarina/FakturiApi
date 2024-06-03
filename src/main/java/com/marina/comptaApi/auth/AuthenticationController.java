package com.marina.comptaApi.auth;


import com.marina.comptaApi.Models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;

    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(
            @RequestBody  RegistrationRequest request
    ) {
        service.register(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody  AuthenticateRequest request
    ) {
        return ResponseEntity.accepted().body(service.authenticate(request));
    }

    @PostMapping("/change-password")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> changePassword(
            @RequestBody ChangePasswordRequest request, Principal utilisateurConnecte
    ) {
        service.changePassword(request,utilisateurConnecte);
        return ResponseEntity.accepted().body("Mot de passe modifié avec succès");
    }

    @PostMapping("/updateProfile")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> updateProfile(
            @RequestBody UpdateProfilRequest user, Principal utilisateurConnecte
    ) {
        service.updateProfile(user,utilisateurConnecte);
        return ResponseEntity.accepted().body("Profil modifié avec succès");
    }

    @GetMapping("/profile")
    public User getProfile(){
        return service.getProfile();
    }



}
