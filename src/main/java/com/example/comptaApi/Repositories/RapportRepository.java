package com.example.comptaApi.Repositories;

import com.example.comptaApi.Models.Rapport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface RapportRepository extends JpaRepository<Rapport, Integer> {
    Rapport findByDate_creation(Date date_creation);
}
