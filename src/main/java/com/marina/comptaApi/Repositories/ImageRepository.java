package com.marina.comptaApi.Repositories;


import com.marina.comptaApi.Models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository  extends JpaRepository<Image,Long> {
}
