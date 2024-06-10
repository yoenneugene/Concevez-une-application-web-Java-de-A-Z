package com.example.demo.service;


import com.example.demo.model.Profil;
import com.example.demo.model.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MoneyTransferService {

    private TransactionService transactionService;

    @Autowired
    public MoneyTransferService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void transferMoney(Profil expediteur, Profil destinataire, float amount, String description) {
        if (expediteur.getArgent() >= amount) {
            expediteur.setArgent(expediteur.getArgent() - amount);
            destinataire.setArgent(destinataire.getArgent() + amount);

            transactionService.createTransaction(new Transaction(expediteur, destinataire.getAdresseMail(), amount, description));
        } else {
            throw new RuntimeException("Le solde de l'expéditeur est insuffisant pour effectuer le transfert.");
        }
    }
}
