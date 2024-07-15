package com.marina.comptaApi.Services;


import com.marina.comptaApi.Models.Solde;
import com.marina.comptaApi.Repositories.SoldeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SoldeService {

    private final SoldeRepository soldeRepository;
    public Solde soldeActuel(Long id){
        return soldeRepository.findFirstByUserIdOrderByDateDesc(id);
    }
}
