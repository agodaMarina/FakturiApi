//package com.marina.comptaApi.Controllers;
//
//
//import com.marina.comptaApi.Models.Rapport;
//import com.marina.comptaApi.Services.RapportService;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//
//
//@RestController
//@RequestMapping("/rapport")
//@CrossOrigin("*")
//@RequiredArgsConstructor
//public class RapportController {
//
//private final RapportService rapportService;
//    @GetMapping("/monthly/{year}/{month}")
//    public ResponseEntity<Rapport> getMonthlyReportData(@PathVariable int year, @PathVariable int month) {
//        try {
//            Rapport reportData = rapportService.generateMonthlyReportData(year, month);
//            return ResponseEntity.ok(reportData);
//        } catch (RuntimeException e) {
//            // Gérer les erreurs (log, message plus spécifique...)
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(null);
//        }
//    }
//
//    @PostMapping("/send/{year}/{month}")
//    public ResponseEntity<String> generateAndSendMonthlyReport(
//            @PathVariable int year,
//            @PathVariable int month
//    ) {
//        try {
//            rapportService.generateAndSendMonthlyReport(year, month);
//            return ResponseEntity.ok("Rapport envoyé avec succès !");
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Erreur lors de la génération ou de l'envoi du rapport.");
//        }
//    }
//
//}
