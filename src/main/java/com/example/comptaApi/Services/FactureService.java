package com.example.comptaApi.Services;

import com.example.comptaApi.Models.Facture;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;



public interface FactureService {

    public String getImageString(MultipartFile multipartFile) throws TesseractException, IOException;
    public void addFacture(Facture facture);
    public void deleteFacture(Long id);
    public Facture getFacture();
    public List<Facture> getAllFactures();
    public void getFactureByProprietaire();

}
