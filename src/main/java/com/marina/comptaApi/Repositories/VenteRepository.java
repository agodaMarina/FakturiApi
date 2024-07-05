package com.marina.comptaApi.Repositories;


import com.marina.comptaApi.Models.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenteRepository extends JpaRepository<Vente, Long> {

}
