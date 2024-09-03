package com.marina.comptaApi.Controllers;

import com.marina.comptaApi.Models.Categorie;
import com.marina.comptaApi.Services.CategorieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorie")
@RequiredArgsConstructor
public class CategorieController {

    private final CategorieService categorieService;

    @GetMapping("/all")
    public Optional<List<Categorie>> getAllCategories(){
        return categorieService.get();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategorie(@RequestBody Categorie categorie){
         categorieService.save(categorie);
        return  ResponseEntity.status(200).body("Categorie ajoutée avec succès");
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategorie(@PathVariable Long id){
        categorieService.delete(id);
    }
}
