package com.marina.comptaApi.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.time.LocalDateTime;

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
    private LocalDateTime date;
    @Column
    private String numero;
    @Column
    private double tva;
    @Column
    private double totaltva;
    @Column
    private double totalttc;

//    @ManyToOne
//    @JoinColumn(name = "compte_id")
//    private Compte compte;


}
