package com.marina.comptaApi.Models;



import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
    private LocalDateTime date;

//    @OneToMany(mappedBy = "facture")
//    private List<Facture> contenu;

}
