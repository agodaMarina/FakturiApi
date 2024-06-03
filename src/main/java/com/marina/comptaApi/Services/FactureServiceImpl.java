package com.marina.comptaApi.Services;

import com.marina.comptaApi.Models.Facture;
import com.marina.comptaApi.Repositories.FactureRepository;
import lombok.Data;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Service
@Data
public class FactureServiceImpl implements FactureService {
    private final FactureRepository factureRepository;

    private final Tesseract tesseract;
    public static final String BASEURL="C:\\Users\\AGODA Marina\\Documents\\IPNET INSTITUTE OF TECHNOLOGY\\STAGE\\TRUSTLINE\\Projet Comptabilité\\captures";


    File file = new File("info.txt");
    FileWriter fileWriter = new FileWriter(file, false);
    private BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

    public FactureServiceImpl(FactureRepository factureRepository, Tesseract tesseract) throws IOException {
        this.factureRepository = factureRepository;
        this.tesseract = tesseract;
    }


    @Override
    public String getImageString(MultipartFile multipartFile) throws TesseractException, IOException {
        final String orginalFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Path filePath = Paths.get(BASEURL+"\\"+orginalFileName);
        final String orcToString = tesseract.doOCR(new File(String.valueOf(filePath)));
        bufferedWriter.write(orcToString);

        return orcToString;
    }


    /*
    public String extractTextFromImage(MultipartFile file) {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("/usr/share/tesseract-ocr/4.00/tessdata"); // Mettez le chemin vers vos données Tesseract
        try {
            return tesseract.doOCR(convert(file));
        } catch (TesseractException | IOException e) {
            e.printStackTrace();
            return "Error during OCR";
        }
    }

    private File convert(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        file.transferTo(convFile);
        return convFile;
    }*/

    /*public Facture traiterFacture(Facture facture) {
        // Logique de correspondance des comptes
        Compte compte = determinerCompte(facture);
        facture.setCompte(compte);

        return factureRepository.save(facture);
    }*/

    @Override
    public void addFacture(Facture facture) {
        factureRepository.save(facture);
    }

    @Override
    public void deleteFacture(Long id) {
        factureRepository.deleteById(id);
    }

    @Override
    public Facture getFacture() {
        return null;
    }

    @Override
    public List<Facture> getAllFactures() {
        return factureRepository.findAll();
    }

    @Override
    public void getFactureByProprietaire() {

    }
}
