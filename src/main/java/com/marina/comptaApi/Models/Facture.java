package com.marina.comptaApi.Models;


import jakarta.persistence.*;
import lombok.*;

import java.io.File;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
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

    @OneToOne(cascade = CascadeType.ALL)
    private ImageData image;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}
