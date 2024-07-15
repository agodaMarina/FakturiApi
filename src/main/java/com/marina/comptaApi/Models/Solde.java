package com.marina.comptaApi.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Solde {
    @Id
    private Long id;

    private double solde;

    private double soldeCritique;

    private LocalDate date;

    @ManyToOne(  )
    private User user;

}
