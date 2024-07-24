package com.marina.comptaApi.Services;

import com.marina.comptaApi.Models.Rapport;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


public interface RapportService {

     Rapport generateRapport(Rapport rapport);
     void sendRapport();
     void deleteRapport(int id);
     Rapport getRapport(LocalDate date_creation);
     List<Rapport> getAllRapport();
}
