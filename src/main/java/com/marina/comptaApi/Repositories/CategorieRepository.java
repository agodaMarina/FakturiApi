package com.marina.comptaApi.Repositories;

import com.marina.comptaApi.Models.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long>{
    Optional<List<Categorie>>findByUserId(Long id);
}
