package com.example.demo;

import com.example.demo.model.Profil;
import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.service.ProfilService;
import com.example.demo.service.TransactionService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=password"
})
public class TransactionServiceTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ProfilService profilService ;

    @Test
    @Transactional
    @DirtiesContext
    public void testCreateTransaction() {
        // Given
        Profil expediteur = new Profil("expediteur@example.com", 100.0f);
        Profil savedExpediteur = profilService.createProfil(expediteur);
        Transaction transaction = new Transaction(savedExpediteur, "destinataire@example.com", 50.0f, "Description de la transaction");

        // When
        Transaction savedTransaction = transactionService.createTransaction(transaction);

        // Then
        assertNotNull(savedTransaction.getId());
        assertNotNull(transactionRepository.findById(savedTransaction.getId()).orElse(null));
    }

    @Test
    public void testGetAllTransactions() {
        // Given
        Profil expediteur1 = new Profil("expediteur1@example.com", 100.0f);
        Profil expediteur2 = new Profil("expediteur2@example.com", 200.0f);
        Profil savedExpediteur1 = profilService.createProfil(expediteur1);
        Profil savedExpediteur2 = profilService.createProfil(expediteur2);
        Transaction transaction1 = new Transaction(savedExpediteur1, "destinataire1@example.com", 50.0f, "Description de la transaction 1");
        Transaction transaction2 = new Transaction(savedExpediteur2, "destinataire2@example.com", 100.0f, "Description de la transaction 2");
        transactionService.createTransaction(transaction1);
        transactionService.createTransaction(transaction2);

        // When
        List<Transaction> transactions = transactionService.getAllTransactions();

        // Then
        assertEquals(2, transactions.size());
    }

    // Ajoutez d'autres méthodes de test pour les autres fonctionnalités de TransactionService
}