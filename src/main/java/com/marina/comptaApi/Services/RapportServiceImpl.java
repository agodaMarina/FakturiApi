package com.marina.comptaApi.Services;


import com.marina.comptaApi.Models.Rapport;
import com.marina.comptaApi.Repositories.RapportRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@Data
public class RapportServiceImpl implements RapportService {

    private final RapportRepository repository;

    @Override
    public Rapport generateRapport( Rapport rapport) {
//         rapport =

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
    public Rapport getRapport(LocalDate date_creation) {
        return repository.findByDate(date_creation);
    }


    @Override
    public List<Rapport> getAllRapport() {

        return repository.findAll();
    }
}
