package com.marina.comptaApi.Repositories;


import com.marina.comptaApi.Models.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {
}