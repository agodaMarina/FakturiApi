package com.marina.comptaApi.Models;



import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;



import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@Builder
@Entity
public class Rapport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate startDate;
    private LocalDate endDate;
    private double soldeInitial;
    private double soleFinal;
    private double totalDepenses;
    private double totalRevenus;
    @Transient
    private List<Achat> depenses;
    @Transient
    private Map<LocalDate, Double> depensesParJour;
    @Transient
    private Map<String, Double> depensesParCategorie;

    @ManyToOne
    private User user;
}
