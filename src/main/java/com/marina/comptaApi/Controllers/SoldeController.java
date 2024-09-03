package com.marina.comptaApi.Controllers;


import com.marina.comptaApi.Models.Solde;
import com.marina.comptaApi.Models.SoldeHistorique;
import com.marina.comptaApi.Repositories.SoldeHistoriqueRepository;
import com.marina.comptaApi.Services.SoldeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("solde")
@RequiredArgsConstructor
public class SoldeController {
    private final SoldeService soldeService;


    @PostMapping("/update")
    public ResponseEntity<Solde> updateSolde(@RequestBody Solde solde, @RequestParam double montant) {
        return ResponseEntity.ok(soldeService.updateSoldeCritique(solde, montant));
    }

    @GetMapping("/get")
    public ResponseEntity<Solde> getSolde() {
        return ResponseEntity.ok(soldeService.soldeActuel());
    }

//    @GetMapping("/list")
//    public List<Solde> listSolde() {
//        return soldeService.liste();
//    }

    @GetMapping("/getHistorique")
    public List<SoldeHistorique> getHistorique() {
        return soldeService.getHistorique();
    }


}
