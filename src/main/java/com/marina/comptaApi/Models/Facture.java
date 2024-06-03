package com.marina.comptaApi.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String date;
    @Column
    private String numero;
    @Column
    private String proprietaire;
    @Column
    private double tva;
    @Column
    private double totaltva;
    @Column
    private double totalttc;
    @Column
    private File photo;
    @Column
    private String etat;

    @ManyToOne
    @JoinColumn(name = "compte_id")
    private Compte compte;


}
