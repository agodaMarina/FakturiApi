package com.example.comptaApi.Models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Compte {

    @Id
    private Long id;

    private String code;

    private String description;


    @OneToMany(mappedBy = "compte")
    private List<Facture> factures;

}
