package com.marina.comptaApi.auth;


import com.marina.comptaApi.Models.User;
import com.marina.comptaApi.Models.dona.Client;
import com.marina.comptaApi.Models.dona.Comptable;
import com.marina.comptaApi.Repositories.test.ClientRepository;
import com.marina.comptaApi.Repositories.test.ClientService;
import com.marina.comptaApi.Repositories.test.ComptableRepository;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {
    private final AuthenticationService service;
    private final LogoutService logoutService;



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

    @GetMapping("/details/{id}")
    public User getDetails(@PathVariable Long id){
        return service.getOneUser(id);
    }

    @GetMapping("/listClient")
    public List<User>getClient(){
        return service.getClients();
    }

    @GetMapping("/listComptable")
    public List<User> getComptable(){
        return service.getComptable();
    }

}
