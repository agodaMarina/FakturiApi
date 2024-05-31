package com.example.comptaApi.Repositories;

import com.example.comptaApi.Models.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



public interface CompteRepository extends JpaRepository<Compte, Long> {

}
