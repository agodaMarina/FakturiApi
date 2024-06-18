package com.marina.comptaApi.Models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;



@Data
@Entity
public class Compte {

    @Id
    private Long id;

    @Column
    private String code;

    @Column
    private String description;


//    @OneToMany(mappedBy = "compte")
//    private List<Facture> factures;

}
