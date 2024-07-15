package com.marina.comptaApi.Repositories;

import com.marina.comptaApi.Models.Solde;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SoldeRepository extends JpaRepository<Solde,Long> {
    List<Solde>findByUserIdOrderByDateDesc(Long id);
    Solde findFirstByUserIdOrderByDateDesc(Long id);
}
