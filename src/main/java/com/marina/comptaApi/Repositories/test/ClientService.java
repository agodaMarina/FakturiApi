package com.marina.comptaApi.Repositories.test;

import com.marina.comptaApi.Models.dona.Client;
import com.marina.comptaApi.Models.dona.Comptable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public List<Client>getAll(){
        return clientRepository.findAll();
    }

    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Transactional
    public Client updateClient(Long clientId, Comptable comptable) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            client.setComptable(comptable);
            return clientRepository.save(client);
        } else {
            throw new IllegalArgumentException("Client not found with ID: " + clientId);
        }
    }
}
