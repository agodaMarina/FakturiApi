//package com.marina.comptaApi.Repositories;
//
//
//import com.marina.comptaApi.Models.Facture;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//
//@Repository
//public interface FactureRepository extends JpaRepository<Facture, Long> {
//    List<Facture> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
//}