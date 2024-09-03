package com.marina.comptaApi.Controllers;

import com.marina.comptaApi.Models.User;
import com.marina.comptaApi.Models.dona.Client;
import com.marina.comptaApi.Models.dona.Comptable;
import com.marina.comptaApi.Repositories.test.ClientService;
import com.marina.comptaApi.Repositories.test.ComptableService;
import com.marina.comptaApi.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AuthenticationService service;
    private final ClientService clientservice;
    private final ComptableService comptableservice;


    @GetMapping("/utilisateurs")
    public List <User> getUsers(){
        return service.findUsers();
    }

    @PostMapping("/blockUser")
    public ResponseEntity<String> desactiveAccount(@RequestParam Long id){
        service.LockAccount(id);
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body("Le compte a été bloqué avec succès");
    }

    @PostMapping("/unlock")
    public ResponseEntity<String> activeAccount(@RequestParam Long id){
        service.unLockAccount(id);
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body("Le compte a été activé avec succès");
    }

    @GetMapping("/utilisateur/{id}")
    public User getUser(@PathVariable Long id){
        return service.getOneUser(id);
    }

    @GetMapping("/listClient")
    public List<Client>getClient(){
        return clientservice.getAll();
    }

    @GetMapping("/listComptable")
    public List<Comptable> getComptable(){
        return comptableservice.getAll();
    }

    @PutMapping("/{clientId}/update")
    public ResponseEntity<Client> updateClientRole(@PathVariable Long clientId, @RequestBody Comptable comptable) {
        Client updatedClient = clientservice.updateClient(clientId, comptable);
        return ResponseEntity.ok(updatedClient);
    }

    @GetMapping("/client/{id}")
    public Client getClient(@PathVariable Long id){
        return clientservice.findById(id).orElse(null);
    }

    @GetMapping("/comptable/{id}")
    public Comptable getComptable(@PathVariable Long id){
        return comptableservice.findById(id);
    }

}
