package com.marina.comptaApi.Repositories;

import com.marina.comptaApi.Models.Solde;
import com.marina.comptaApi.Models.SoldeHistorique;
import com.marina.comptaApi.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface SoldeHistoriqueRepository extends JpaRepository<SoldeHistorique,Long> {

    Solde findByUtilisateurAndDateLessThanEqual(  User profile, LocalDate startDate);
}
