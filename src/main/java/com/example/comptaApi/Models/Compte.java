package com.example.comptaApi.Models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Compte {

    @Id
    private Long id;

    private String numero_compte;

    private String description;

    private String type;

    private String classe;

    private String Categorie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero_compte() {
        return numero_compte;
    }

    public void setNumero_compte(String numero_compte) {
        this.numero_compte = numero_compte;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getCategorie() {
        return Categorie;
    }

    public void setCategorie(String categorie) {
        Categorie = categorie;
    }
}
