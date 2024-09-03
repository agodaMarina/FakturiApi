package com.marina.comptaApi.Services;

import com.marina.comptaApi.Models.Revenu;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RevenuService {

    Revenu save(Revenu revenu) ;

    Revenu update(Revenu revenu);

    void delete(Long id);

    List<Revenu>getAll();

    Revenu get(Long id);
}
