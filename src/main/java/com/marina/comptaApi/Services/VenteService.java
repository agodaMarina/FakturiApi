package com.marina.comptaApi.Services;

import com.marina.comptaApi.Models.Vente;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VenteService {

    Vente save(Vente vente, MultipartFile file) throws Exception;

    Vente update(Vente vente);

    void delete(Long id);

    List<Vente>getAll();

    Vente get(Long id);
}
