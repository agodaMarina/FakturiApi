package com.marina.comptaApi.Services;

import com.marina.comptaApi.Models.Achat;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AchatService {

    Achat save(Achat achat, MultipartFile file) throws IOException;

    Optional<List<Achat>> findByUser();

    Achat update(Achat achat);

    void delete(Long id);

    Achat findById(Long id);

    List<Achat> findAll();

    ByteArrayInputStream exportFacturesToExcel(List<Long>ids) throws IOException;

    List<Achat> findBetweenDates(LocalDate date1, LocalDate date2);

}
