package com.marina.comptaApi.Controllers;

import com.marina.comptaApi.Models.Facture;
import com.marina.comptaApi.Services.FactureService;
import lombok.AllArgsConstructor;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/facture")
@CrossOrigin("*")
@AllArgsConstructor
public class FactureController {

    private final FactureService factureService;

//    @GetMapping("/addImage")
//    public ResponseEntity<String> getImageToString(@RequestParam MultipartFile multipartFile) throws TesseractException, IOException {
//        Map<String, String> extractedData = new HashMap<>();
//
//        /*
//        *String fournisseur = extractInfo(result, "Fournisseur:\\s*(.*)");
//            String date = extractInfo(result, "Date:\\s*(\\d{2}/\\d{2}/\\d{4})");
//            String montant = extractInfo(result, "Montant:\\s*([\\d,\\.]+)");
//
//            extractedData.put("Fournisseur", fournisseur);
//            extractedData.put("Date", date);
//            extractedData.put("Montant", montant);
//
//        } catch (TesseractException | IOException e) {
//            e.printStackTrace();
//            extractedData.put("Erreur", "Erreur lors de l'analyse du fichier.");
//        }
//
//        return extractedData;
//
//        private String extractInfo(String text, String patternString) {
//        Pattern pattern = Pattern.compile(patternString, Pattern.MULTILINE);
//        Matcher matcher = pattern.matcher(text);
//        if (matcher.find()) {
//            return matcher.group(1).trim();
//        }
//        return "Non trouvé";
//    }
//     @PostMapping("/validate")
//    public String validateData(@RequestBody Map<String, String> validatedData) {
//        // Ici, vous pouvez ajouter la logique pour enregistrer les données dans la base de données
//        // Par exemple, en utilisant un service pour sauvegarder les informations extraites
//
//        return "Données validées et enregistrées avec succès";
//    }
//
//         */
//
//        return new ResponseEntity<>(factureService
//                .getImageString(multipartFile), HttpStatus.OK);
//    }

    @PostMapping("/addFacture")
    public ResponseEntity<String> ajoutFactureManuelle(@RequestBody Facture facture){

        factureService.addFacture(facture);
        return new ResponseEntity<>("Facture ajoutée avec succès", HttpStatus.OK);
    }

    @GetMapping("/getFacture")
    public List<Facture> getFacture(){

        return factureService.getAllFactures();
    }
    @GetMapping("/export/excel")
    public ResponseEntity<InputStreamResource> exportFacturesToExcel() throws IOException {
        ByteArrayInputStream in = factureService.exportFacturesToExcel();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=factures.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(new InputStreamResource(in));
    }

    @DeleteMapping("/deleteFacture/{id}")
    public void deleteFacture(@PathVariable Long id){
        factureService.deleteFacture(id);
    }

}
