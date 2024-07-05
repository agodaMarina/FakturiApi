package com.marina.comptaApi.Repositories;


import com.marina.comptaApi.Models.Achat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchatRepository extends JpaRepository<Achat,Long> {
}