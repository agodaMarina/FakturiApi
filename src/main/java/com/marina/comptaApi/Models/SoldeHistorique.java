package com.marina.comptaApi.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoldeHistorique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User utilisateur;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private double montant;

    @Column
    private double soldeinitial;

    @Column
    private String typeOeration;
}