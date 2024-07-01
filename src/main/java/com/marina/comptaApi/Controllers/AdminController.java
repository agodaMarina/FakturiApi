package com.marina.comptaApi.Controllers;

import com.marina.comptaApi.Models.User;
import com.marina.comptaApi.auth.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AuthenticationService service;

    public AdminController(AuthenticationService service) {
        this.service = service;
    }

    @GetMapping("/utilisateurs")
    public List <User> getUsers(){
        return service.findUsers();
    }

    @PostMapping("/blockUser/{id}")
    public ResponseEntity<String> desactiveAccount(@PathVariable Long id){
        service.LockAccount(id);
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body("Le compte a été bloqué avec succès");
    }

    @PostMapping("/unlock/{id}")
    public ResponseEntity<String> activeAccount(@PathVariable Long id){
        service.unLockAccount(id);
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body("Le compte a été activé avec succès");
    }

    @GetMapping("/utilisateur/{id}")
    public User getUser(@PathVariable Long id){
        return service.getOneUser(id);
    }


}
