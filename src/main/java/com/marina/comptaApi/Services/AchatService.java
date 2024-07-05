package com.marina.comptaApi.Services;

import com.marina.comptaApi.Models.Achat;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AchatService {

    Achat save(Achat achat, MultipartFile file) throws IOException;

    Achat update(Achat achat);

    void delete(Long id);

    Achat findById(Long id);

    List<Achat> findAll();
}
