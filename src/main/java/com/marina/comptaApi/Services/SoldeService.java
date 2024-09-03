package com.marina.comptaApi.Services;


import com.marina.comptaApi.Models.Solde;
import com.marina.comptaApi.Models.SoldeHistorique;
import com.marina.comptaApi.Models.User;
import com.marina.comptaApi.Repositories.SoldeHistoriqueRepository;
import com.marina.comptaApi.Repositories.SoldeRepository;
import com.marina.comptaApi.auth.AuthenticationService;
import com.marina.comptaApi.exception.SoldeNegatifException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SoldeService {


    private final SoldeRepository soldeRepository;
    private final AuthenticationService authService;
    private final SoldeHistoriqueRepository soldeHistoriqueRepository;

    public Solde soldeActuel() {
        return soldeRepository.findByUser(authService.getProfile());
    }

    @Transactional
    public void updateSolde(User user, double montant, String typeOperation) throws SoldeNegatifException {
        Solde solde = soldeActuel();
        double montantInitial = solde.getSolde();
        if (typeOperation.equals("DEPENSE")) {

            if (solde.getSolde() < montant) {
                throw new SoldeNegatifException("Le montant de votre dépense est supérieur à votre solde !");
            } else {

                solde.setSolde(montantInitial - montant);
                soldeRepository.save(solde);
            }

        } else if (typeOperation.equals("REVENU")) {
            solde.setSolde(montantInitial + montant);
            soldeRepository.save(solde);
        }
        //création de l'historique
        SoldeHistorique historique = new SoldeHistorique();
        historique.setMontant(montant);
        historique.setUtilisateur(user);
        historique.setSoldeinitial(montantInitial);
        historique.setDate(LocalDate.now());
        historique.setTypeOeration(typeOperation);

        soldeHistoriqueRepository.save(historique);
    }

    public List<SoldeHistorique> getHistorique(){
        return soldeHistoriqueRepository.findAll();
    }

    public Solde updateSoldeCritique(Solde solde, double montant){
         solde.setSoldeCritique(montant);
            return soldeRepository.save(solde);
    }


}
