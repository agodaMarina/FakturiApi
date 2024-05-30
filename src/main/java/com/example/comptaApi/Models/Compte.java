package com.example.comptaApi.Models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

public class Compte {

    @Id
    private Long id;

    private String numero_compte;

    private String description;

    private String type;

    private String classe;

    private String Categorie;

    @OneToMany(mappedBy = "compte")
    private List<Facture> factures;

}
