package com.marina.comptaApi.Models;



import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Rapport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String titre;

    @CreatedDate
    private LocalDate dateDebut;

    private LocalDate dateFin;

    @OneToMany()
    private List<Achat> achats;

    @OneToOne()
    private User user;
}
