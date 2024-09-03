package com.marina.comptaApi.Repositories.test;

import com.marina.comptaApi.Models.dona.Comptable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComptableRepository extends JpaRepository<Comptable,Long> {


}
