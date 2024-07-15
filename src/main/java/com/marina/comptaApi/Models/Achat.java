package com.marina.comptaApi.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Achat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = true)
    private LocalDateTime dateEmission;
    @Column
    private String numero;

    @Column
    private double tva;
    @Column
    private double totaltva;
    @Column
    private double totalttc;

    private boolean statut;

    @OneToOne(cascade = CascadeType.MERGE)
    private ImageData image;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    private String fournisseur;

    @Column(nullable = true)
    private LocalDate dateEcheance;

}
