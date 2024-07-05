package com.marina.comptaApi.Controllers;

import com.marina.comptaApi.Models.Vente;
import com.marina.comptaApi.Services.VenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/vente")
@RequiredArgsConstructor
public class VenteController {
    private final VenteService venteService;

    @PostMapping("/validate")
    public ResponseEntity<?>saveVente(@RequestPart Vente vente, @RequestPart MultipartFile file) throws Exception {
        return ResponseEntity.ok(venteService.save(vente,file));
    }

    @GetMapping("/all")
    public ResponseEntity<?>getAllVentes() {
        return ResponseEntity.ok(venteService.getAll());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?>getVenteById(@PathVariable Long id) {
        return ResponseEntity.ok(venteService.get(id));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?>deleteVente(@PathVariable Long id) {
        venteService.delete(id);
        return ResponseEntity.ok("Vente supprimée avec succès");
    }

    @PutMapping("update")
    public ResponseEntity<?>updateVente(@RequestBody Vente vente) {
        return ResponseEntity.ok(venteService.update(vente));
    }


}
