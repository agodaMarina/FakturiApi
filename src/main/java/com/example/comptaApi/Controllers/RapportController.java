package com.example.comptaApi.Controllers;


import com.example.comptaApi.Models.Rapport;
import com.example.comptaApi.Services.RapportService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/rapport")
@Data
public class RapportController {

    private final RapportService rapportService;

    @PostMapping("/CreateRapport")
    public Rapport generateRapport(@RequestBody Rapport rapport){
        return rapportService.generateRapport( rapport) ;
    }


    @GetMapping("/getRapport/{date}")
    public Rapport getRapport(@PathVariable Date date){
        return rapportService.getRapport(date);
    }


    @GetMapping("/getAllReports")
    public List<Rapport> getAllRapport(){
        return rapportService.getAllRapport();
    }


    @PutMapping("/updateRapport")
    public void updateRapport(@RequestBody Rapport rapport){
        rapportService.updateRapport(rapport);
    }


    @DeleteMapping("/deleteRapport/{id}")
    public void deleteRapport(@PathVariable int id){
        rapportService.deleteRapport(id);
    }


}
