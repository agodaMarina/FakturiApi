package com.marina.comptaApi.Controllers;


import com.marina.comptaApi.Models.Achat;
import com.marina.comptaApi.Services.AchatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("achat")
@RequiredArgsConstructor
public class AchatController {
    private final AchatService achatService;

    @PostMapping("/add")
    public ResponseEntity<Achat> addAchat(@RequestPart Achat achat, @RequestPart MultipartFile file) throws IOException {
        achatService.save(achat, file);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAchats(){
            achatService.findAll();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getAchat(@PathVariable Long id){
        achatService.findById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAchat(@RequestBody Achat achat){
        achatService.update(achat);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAchat(@PathVariable Long id){
        achatService.delete(id);
        return ResponseEntity.ok().build();
    }

}
