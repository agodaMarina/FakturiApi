package com.marina.comptaApi.Models;


import jakarta.persistence.*;
import lombok.*;

import java.io.File;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "compte_id", nullable = true)
//    private Compte compte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}
