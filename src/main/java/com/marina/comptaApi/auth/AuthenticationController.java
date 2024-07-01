package com.marina.comptaApi.auth;


import com.marina.comptaApi.Models.User;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {
    private final AuthenticationService service;
    private final LogoutService logoutService;


    public AuthenticationController(AuthenticationService service, LogoutService logoutService, LogoutService logoutService1) {
        this.service = service;
        this.logoutService = logoutService1;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(@Valid
            @RequestBody  RegistrationRequest request
    ) throws MessagingException {
        service.register(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/activate_account")
    public void activeAccount(@RequestParam String token) throws MessagingException {
        service.activateAccount(token);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<AuthenticationResponse> login(@Valid
            @RequestBody  AuthenticateRequest request
    ) {
        return ResponseEntity.accepted().body(service.authenticate(request));
    }

    @PostMapping("/change-password")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> changePassword(@Valid
            @RequestBody ChangePasswordRequest request, Principal utilisateurConnecte
    ) {
        service.changePassword(request,utilisateurConnecte);
        return ResponseEntity.accepted().body("Mot de passe modifié avec succès");
    }

    @PostMapping("/updateProfile")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> updateProfile(@Valid
            @RequestBody UpdateProfilRequest user, Principal utilisateurConnecte
    ) {
        service.updateProfile(user,utilisateurConnecte);
        return ResponseEntity.accepted().body("Profil modifié avec succès");
    }

    @GetMapping("/profile")
    public User getProfile(){

        return service.getProfile();
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logoutService.logout(request, response, authentication);
        return ResponseEntity.status(HttpStatus.OK).body("Logout successful");
    }

}
