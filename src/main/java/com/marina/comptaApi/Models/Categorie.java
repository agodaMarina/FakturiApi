package com.marina.comptaApi.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Entity
@Data
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "Le libelle ne peut pas etre vide")
    @NotEmpty(message = "Le libelle ne peut pas etre vide")
    private String libelle;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

}
