package com.example.comptaApi.Services;

import com.example.comptaApi.Models.Rapport;

public interface RapportService {

    public void generateRapport();
    public void sendRapport();
    public void saveRapport();
    public void deleteRapport();
    public void updateRapport();
    public Rapport getRapport();
}
