package com.example.demo.service;

import com.example.demo.model.Profil;
import com.example.demo.repository.ProfilRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class ProfilService {

    private ProfilRepository profilRepository;

    @Autowired
    public ProfilService(ProfilRepository profilRepository) {
        this.profilRepository = profilRepository;
    }

    public Profil createProfil(Profil profil) {
        return profilRepository.save(profil);
    }

    public List<Profil> getAllProfils() {
        return profilRepository.findAll();
    }

    public Optional<Profil> getProfilById(Long id) {
        return profilRepository.findById(id);
    }

    public Profil updateProfil(Long id, Profil updatedProfil) {
        Optional<Profil> existingProfilOptional = profilRepository.findById(id);
        if (existingProfilOptional.isPresent()) {
            updatedProfil.setId(id);
            return profilRepository.save(updatedProfil);
        } else {
            // Gérer l'exception ou retourner null ou une valeur par défaut selon votre besoin
            throw new RuntimeException("Profil not found with id: " + id);
        }
    }

    public void deleteProfil(Long id) {
        profilRepository.deleteById(id);
    }
    public Profil findByEmail(String email) {
       Profil profil = profilRepository.findByAdresseMail(email);
        return profil;
    }

}


