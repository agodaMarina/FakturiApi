package com.marina.comptaApi.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String client;

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
