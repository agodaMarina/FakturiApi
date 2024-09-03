package com.marina.comptaApi.Repositories.test;

import com.marina.comptaApi.Models.dona.Comptable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ComptableService {
    private final ComptableRepository comptableRepository;

    @Transactional
    public List<Comptable>getAll(){
        return comptableRepository.findAll();
    }

    public Comptable findById(Long id) {
        return comptableRepository.findById(id).orElse(null);
    }

}
