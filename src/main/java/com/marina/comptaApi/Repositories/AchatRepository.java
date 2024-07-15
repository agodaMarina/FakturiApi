package com.marina.comptaApi.Repositories;


import com.marina.comptaApi.Models.Achat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AchatRepository extends JpaRepository<Achat,Long> {
    Optional<List<Achat>>findByUserId(Long id);
}