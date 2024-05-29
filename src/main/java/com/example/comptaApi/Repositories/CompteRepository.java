package com.example.comptaApi.Repositories;

import com.example.comptaApi.Models.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteRepository extends JpaRepository<Compte, Long> {
}
