package com.example.demo;


import com.example.demo.model.Profil;
import com.example.demo.repository.ProfilRepository;
import com.example.demo.service.ProfilService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=password"
})
public class ProfilServiceTest {

    @Autowired
    private ProfilRepository profilRepository;

    @Autowired
    private ProfilService profilService;

    @Test
    @Transactional
    @DirtiesContext
    public void testCreateProfil() {
        Profil profil = new Profil("test@example.com", 100.0f);

        // When
        Profil savedProfil = profilService.createProfil(profil);

        // Then
        assertNotNull(savedProfil.getId()); // Vérifie si l'ID du profil a été attribué
        assertNotNull(profilRepository.findById(savedProfil.getId()).orElse(null));
    }
    @Test
    public void testGetAllProfils() {
        // Given
        Profil profil1 = new Profil("test1@example.com", 100.0f);
        Profil profil2 = new Profil("test2@example.com", 200.0f);
        profilService.createProfil(profil1);
        profilService.createProfil(profil2);

        // When
        List<Profil> profils = profilService.getAllProfils();

        // Then
        assertEquals(2, profils.size());
    }

    @Test
    public void testGetProfilById() {
        // Given
        Profil profil = new Profil("test@example.com", 100.0f);
        Profil savedProfil = profilService.createProfil(profil);

        // When
        Optional<Profil> retrievedProfil = profilService.getProfilById(savedProfil.getId());

        // Then
        assertEquals(savedProfil, retrievedProfil.orElse(null));
    }

    @Test
    public void testUpdateProfil() {
        // Given
        Profil profil = new Profil("test@example.com", 100.0f);
        Profil savedProfil = profilService.createProfil(profil);
        savedProfil.setArgent(200.0f);

        // When
        Profil updatedProfil = profilService.updateProfil(savedProfil.getId(), savedProfil);

        // Then
        assertEquals(200.0f, updatedProfil.getArgent());
    }

    @Test
    public void testUpdateProfilNotFound() {
        // Given
        Profil profil = new Profil("test@example.com", 100.0f);
        profil.setId(999L); // Profil inexistant

        // When/Then
        assertThrows(RuntimeException.class, () -> profilService.updateProfil(profil.getId(), profil));
    }

    @Test
    public void testDeleteProfil() {
        // Given
        Profil profil = new Profil("test@example.com", 100.0f);
        Profil savedProfil = profilService.createProfil(profil);

        // When
        profilService.deleteProfil(savedProfil.getId());

        // Then
        Optional<Profil> retrievedProfil = profilRepository.findById(savedProfil.getId());
        assertEquals(Optional.empty(), retrievedProfil);
    }



}