package com.marina.comptaApi.Repositories;

import com.marina.comptaApi.Models.Rapport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;

@Repository
public interface RapportRepository extends JpaRepository<Rapport, Integer> {
    Rapport findByDate(LocalDate date_creation);
}
