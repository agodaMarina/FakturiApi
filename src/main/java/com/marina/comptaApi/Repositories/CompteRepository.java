package com.marina.comptaApi.Repositories;

import com.marina.comptaApi.Models.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {

}
