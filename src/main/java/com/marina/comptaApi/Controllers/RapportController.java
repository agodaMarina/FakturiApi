//package com.marina.comptaApi.Controllers;
//
//
//import com.marina.comptaApi.Models.Rapport;
//import com.marina.comptaApi.Services.GenRapport;
//import com.marina.comptaApi.Services.RapportService;
//import lombok.Data;
//import org.springframework.web.bind.annotation.*;
//import com.marina.comptaApi.Services.GenRapport;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//
//import java.time.LocalDate;
//import java.util.Date;
//import java.util.List;
//
//@RestController
//@RequestMapping("/rapport")
//@CrossOrigin("*")
//@Data
//public class RapportController {
//
//    private final RapportService rapportService;
//    private final GenRapport genRapport;
//
//    @PostMapping("/save")
//    public Rapport generateRapport(@RequestBody Rapport rapport){
//        return rapportService.generateRapport( rapport) ;
//    }
//
//
//    @GetMapping("/generate-report")
//    public ResponseEntity<byte[]> generateReport(@RequestParam String startDate,
//                                                 @RequestParam String endDate,
//                                                 @RequestParam String username) throws IOException {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
//        LocalDateTime end = LocalDateTime.parse(endDate, formatter);
//
//        byte[] pdfData = genRapport.generatePdf(start, end, username);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", "inline; filename=report.pdf");
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
//                .body(pdfData);
//    }
//
//    @GetMapping("/getRapport/{date}")
//    public Rapport getRapport(@PathVariable LocalDate date){
//        return rapportService.getRapport(date);
//    }
//
//
//    @GetMapping("/getAllReports")
//    public List<Rapport> getAllRapport(){
//        return rapportService.getAllRapport();
//    }
//
//
//    @PutMapping("/updateRapport")
//    public void updateRapport(@RequestBody Rapport rapport){
//        rapportService.updateRapport(rapport);
//    }
//
//
//    @DeleteMapping("/deleteRapport/{id}")
//    public void deleteRapport(@PathVariable int id){
//        rapportService.deleteRapport(id);
//    }
//
//
//}
