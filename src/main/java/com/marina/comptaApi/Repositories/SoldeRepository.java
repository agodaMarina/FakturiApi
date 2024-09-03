package com.marina.comptaApi.Repositories;

import com.marina.comptaApi.Models.Solde;
import com.marina.comptaApi.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SoldeRepository extends JpaRepository<Solde,Long> {

    Solde findByDate(LocalDate date);
    Solde findByUserAndDateLessThanEqual(User profile, LocalDate startDate);
    Solde findByUser(User user);
}
