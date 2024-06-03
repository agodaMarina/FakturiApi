package com.marina.comptaApi.Models;



import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.Date;
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Rapport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String titre;


    @CreatedDate
    private LocalDate date;

    private String Contenu;

}
