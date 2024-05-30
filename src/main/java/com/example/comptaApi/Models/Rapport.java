package com.example.comptaApi.Models;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
@Data

public class Rapport {
    @Id
    private int id;

    private String titre;

    @CreatedDate
    private Date date_creation;

    private String Contenu;



}
