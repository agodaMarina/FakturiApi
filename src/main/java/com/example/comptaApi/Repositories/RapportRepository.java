package com.example.comptaApi.Repositories;

import com.example.comptaApi.Models.Rapport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface RapportRepository extends JpaRepository<Rapport, Integer> {
    Optional<Rapport> findByDate_creation(Date date_creation);
}
