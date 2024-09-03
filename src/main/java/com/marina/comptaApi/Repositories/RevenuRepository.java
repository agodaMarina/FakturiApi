package com.marina.comptaApi.Repositories;


import com.marina.comptaApi.Models.Revenu;
import com.marina.comptaApi.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RevenuRepository extends JpaRepository<Revenu, Long> {

    List<Revenu>findByUser(User user);

}
