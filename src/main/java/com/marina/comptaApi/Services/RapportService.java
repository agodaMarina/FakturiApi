package com.marina.comptaApi.Services;

import com.marina.comptaApi.Models.Rapport;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


public interface RapportService {

    public Rapport generateRapport(Rapport rapport);
    public void sendRapport();
    public void deleteRapport(int id);
    public void updateRapport(Rapport rapport);
    public Rapport getRapport(LocalDate date_creation);
    public List<Rapport> getAllRapport();
}
