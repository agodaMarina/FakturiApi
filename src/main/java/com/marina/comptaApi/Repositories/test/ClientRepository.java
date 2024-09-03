package com.marina.comptaApi.Repositories.test;

import com.marina.comptaApi.Models.dona.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
}
