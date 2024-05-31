package com.example.comptaApi.Services;


import com.example.comptaApi.Models.Rapport;
import com.example.comptaApi.Repositories.RapportRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Data
public class RapportServiceImpl implements RapportService {

    private final RapportRepository repository;

    @Override
    public Rapport generateRapport( Rapport rapport) {
         return repository.save(rapport);
    }

    @Override
    public void sendRapport() {
        //toDo send rapport
    }

    @Override
    public void deleteRapport(int id) {
        repository.deleteById(id);
    }

    @Override
    public void updateRapport(Rapport rapport) {
        repository.save(rapport);
    }


    @Override
    public Rapport getRapport(Date date_creation) {
        return repository.findByDate_creation(date_creation);
    }


    @Override
    public List<Rapport> getAllRapport() {
        return repository.findAll();
    }
}
