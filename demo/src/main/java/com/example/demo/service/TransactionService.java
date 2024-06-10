package com.example.demo.service;


import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransactionService {

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
        Optional<Transaction> existingTransactionOptional = transactionRepository.findById(id);
        if (existingTransactionOptional.isPresent()) {
            updatedTransaction.setId(id);
            return transactionRepository.save(updatedTransaction);
        } else {
            throw new RuntimeException("Transaction not found with id: " + id);
        }
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
