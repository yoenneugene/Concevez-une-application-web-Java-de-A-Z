package com.example.demo.repository;


import com.example.demo.model.Profil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfilRepository extends JpaRepository<Profil, Long> {
  Profil findByAdresseMail(String adresseMail);
}
