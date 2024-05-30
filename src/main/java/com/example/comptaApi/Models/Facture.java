package com.example.comptaApi.Models;


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

public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String date;
    private String numero;
    private String proprietaire;
    private double tva;
    private double totaltva;
    private double totalttc;
    private File photo;
    private String etat;

    @ManyToOne
    @JoinColumn(name = "compte_id")
    private Compte compte;


}
