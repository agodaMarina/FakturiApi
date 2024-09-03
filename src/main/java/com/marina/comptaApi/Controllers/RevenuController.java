package com.marina.comptaApi.Controllers;

import com.marina.comptaApi.Models.Revenu;
import com.marina.comptaApi.Services.RevenuService;
import com.marina.comptaApi.exception.SoldeNegatifException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/revenu")
@RequiredArgsConstructor
public class RevenuController {
    private final RevenuService revenuService;

    @PostMapping("/add")
    public ResponseEntity<?> saveRevenu(@RequestBody Revenu revenu) throws Exception {

            revenuService.save(revenu);
            return ResponseEntity.status(200).body("Revenu ajouté avec succès");

    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllRevenu() {
        return ResponseEntity.ok(revenuService.getAll());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> getRevenuById(@PathVariable Long id) {
        return ResponseEntity.ok(revenuService.get(id));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteVente(@PathVariable Long id) {
        revenuService.delete(id);
        return ResponseEntity.ok("Revenu supprimé avec succès");
    }

    @PutMapping("update")
    public ResponseEntity<?> updateRevenu(@RequestBody Revenu revenu) {
        return ResponseEntity.ok(revenuService.update(revenu));
    }


}
