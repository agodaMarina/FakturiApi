package com.marina.comptaApi.Controllers;


import com.marina.comptaApi.Models.Achat;
import com.marina.comptaApi.Services.AchatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("achat")

public class AchatController {
    private final AchatService achatService;

    public AchatController(AchatService achatService) {
        this.achatService = achatService;
    }

    @PostMapping("/add")
    public ResponseEntity<Achat> addAchat(@RequestPart Achat achat, @RequestPart MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(achatService.save(achat, file));
    }

    @GetMapping("/all")
    public Optional<List<Achat>> getAllAchats(){

        return achatService.findByUser();
    }

    @GetMapping("/get/{id}")
    public Achat getAchat(@PathVariable Long id){

        return achatService.findById(id);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAchat(@RequestBody Achat achat){

        return ResponseEntity.ok().body(achatService.update(achat));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAchat(@PathVariable Long id){
        achatService.delete(id);
        return ResponseEntity.ok().build();
    }

}
