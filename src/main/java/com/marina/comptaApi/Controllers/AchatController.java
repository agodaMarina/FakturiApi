package com.marina.comptaApi.Controllers;


import com.marina.comptaApi.Models.Achat;
import com.marina.comptaApi.Services.AchatService;
import com.marina.comptaApi.exception.SoldeNegatifException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("achat")

public class AchatController {
    private final AchatService achatService;

    public AchatController(AchatService achatService) {
        this.achatService = achatService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAchat(@RequestPart Achat achat, @RequestPart MultipartFile file) throws IOException {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(achatService.save(achat, file));
        }catch (SoldeNegatifException e){
            return ResponseEntity.badRequest().body(new IOException(e.getMessage()));
        }

    }

    @GetMapping("/all")
    public Optional<List<Achat>> getAllAchats(){
        return achatService.findByUser();
    }

    @GetMapping("/get/{id}")
    public Achat getAchat(@PathVariable Long id){

        return achatService.findById(id);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAchat(@RequestBody Achat achat){

        return ResponseEntity.ok().body(achatService.update(achat));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAchat(@PathVariable Long id){
        achatService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping ("/export/excel")
    public ResponseEntity<InputStreamResource> exportFacturesToExcel(@RequestBody List<Long>ids) throws IOException {
        ByteArrayInputStream in = achatService.exportFacturesToExcel(ids);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=factures.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(new InputStreamResource(in));
    }

}
