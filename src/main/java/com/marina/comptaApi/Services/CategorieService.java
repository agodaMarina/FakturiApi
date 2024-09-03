package com.marina.comptaApi.Services;


import com.marina.comptaApi.Models.Categorie;
import com.marina.comptaApi.Repositories.CategorieRepository;
import com.marina.comptaApi.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategorieService {

    private final CategorieRepository categorieRepository;
    private final AuthenticationService authService;


    public Categorie save(Categorie categorie){
        categorie.setUser(authService.getProfile());
        return categorieRepository.save(categorie);
    }

    @Transactional
    public Optional<List<Categorie>> get(){
        return categorieRepository.findByUserId(authService.getProfile().getId());
    }

    public void delete(Long id){
        categorieRepository.deleteById(id);
    }

    public Categorie update(Categorie categorie){
        return categorieRepository.save(categorie);
    }

}
