package com.example.demo;


import com.example.demo.model.Profil;
import com.example.demo.model.Transaction;
import com.example.demo.service.MoneyTransferService;
import com.example.demo.service.TransactionService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Transactional
public class MoneyTransferServiceTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private MoneyTransferService moneyTransferService;

    @Test
    public void testTransferMoney() {
        // Given
        Profil expediteur = new Profil("expediteur@example.com", 200.0f);
        Profil destinataire = new Profil("destinataire@example.com", 100.0f);
        float amount = 50.0f;
        String description = "Transfert d'argent";

        // When
        moneyTransferService.transferMoney(expediteur, destinataire, amount, description);

        // Then
        verify(transactionService).createTransaction(new Transaction(expediteur, destinataire.getAdresseMail(), amount, description));
        assertEquals(150.0f, expediteur.getArgent()); // L'expéditeur a perdu 50 d'argent
        assertEquals(150.0f, destinataire.getArgent());

    }

    @Test
    public void testTransferMoneyInsufficientFunds() {
        // Given
        Profil expediteur = new Profil("expediteur@example.com", 50.0f);
        Profil destinataire = new Profil("destinataire@example.com", 100.0f);
        float amount = 100.0f;
        String description = "Transfert d'argent";

        // When/Then
        assertThrows(RuntimeException.class, () -> moneyTransferService.transferMoney(expediteur, destinataire, amount, description));

    }
}

