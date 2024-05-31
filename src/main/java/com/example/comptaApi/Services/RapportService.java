package com.example.comptaApi.Services;

import com.example.comptaApi.Models.Rapport;

import java.util.Date;
import java.util.List;


public interface RapportService {

    public Rapport generateRapport(Rapport rapport);
    public void sendRapport();
    public void deleteRapport(int id);
    public void updateRapport(Rapport rapport);
    public Rapport getRapport(Date date_creation);
    public List<Rapport> getAllRapport();
}
