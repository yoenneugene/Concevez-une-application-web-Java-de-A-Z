package com.example.demo.controller;



import com.example.demo.model.Profil;
import com.example.demo.model.Transaction;
import com.example.demo.repository.ProfilRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.service.MoneyTransferService;
import com.example.demo.service.TransactionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    @Autowired
    private ProfilRepository profilRepository;
    @Autowired
    private MoneyTransferService moneyTransferService;
    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/home")
    public String viewTransactions(HttpSession session, Model model) {
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail != null) {
            Profil profil = profilRepository.findByAdresseMail(userEmail);
            List<Transaction> transactions = transactionRepository.findByExpediteur(profil);
            model.addAttribute("transactions", transactions);
            model.addAttribute("userEmail", userEmail);
        }
        return "home";
    }
    @PostMapping("/submitForm")
    public String submitForm(@RequestParam("email") String email,
                             @RequestParam("password") String password,
                             HttpSession session) {
        // Vérification si les paramètres email et password sont présents
        if (email != null && password != null) {
            // Test de connexion factice
            boolean isConnected = isValidCredentials(email, password);

            // Si les identifiants sont valides, redirigez vers une page de succès
            if (isConnected) {
                // Stocker l'adresse e-mail dans la session
                session.setAttribute("userEmail", email);
                return "redirect:/home"; // Vous devez créer une page de succès
            }
        }
        // Si les paramètres sont manquants ou les identifiants sont invalides, redirigez vers une page d'échec
        return "redirect:/error"; // Vous devez créer une page d'erreur
    }

    // Méthode factice pour vérifier les identifiants
    private boolean isValidCredentials(String email, String password) {
        // Implémentez ici la logique de validation des identifiants
     Profil profil = profilRepository.findByAdresseMail(email);

        // Vérifier si le profil existe et si le mot de passe correspond
        return profil != null && profil.getPassword().equals(password);
    }
    @PostMapping("/sendMoney")
    public String sendMoney(@RequestParam("email") String recipientEmail,
                            @RequestParam("amount") float amount,
                            @RequestParam("description") String description,
                            HttpSession session, Model model) {
        String senderEmail = (String) session.getAttribute("userEmail");
        if (senderEmail != null && recipientEmail != null && amount > 0) {
            // Récupérez les profils de l'expéditeur et du destinataire
            Profil expediteur = profilRepository.findByAdresseMail(senderEmail);
         Profil destinataire = profilRepository.findByAdresseMail(recipientEmail);

            try {
                moneyTransferService.transferMoney(expediteur, destinataire, amount, description);
                model.addAttribute("message", "Money sent successfully to " + recipientEmail);
            } catch (RuntimeException e) {
                model.addAttribute("message", e.getMessage());
            }
        } else {
            model.addAttribute("message", "Invalid transaction details. Please check and try again.");
        }
        return "redirect:/home";
    }
    
}