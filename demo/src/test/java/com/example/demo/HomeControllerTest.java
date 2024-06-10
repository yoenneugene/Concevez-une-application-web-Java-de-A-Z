package com.example.demo;

import com.example.demo.controller.HomeController;
import com.example.demo.model.Profil;
import com.example.demo.model.Transaction;
import com.example.demo.repository.ProfilRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.service.MoneyTransferService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Paths.get;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class HomeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProfilRepository profilRepository;

    @Mock
    private MoneyTransferService moneyTransferService;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private HomeController homeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }



    @Test
    public void testViewTransactions() throws Exception {
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("userEmail")).thenReturn("user@example.com");

        Profil profil = new Profil();
        profil.setAdresseMail("user@example.com");
        when(profilRepository.findByAdresseMail("user@example.com")).thenReturn(profil);

        List<Transaction> transactions = new ArrayList<>();
        when(transactionRepository.findByExpediteur(profil)).thenReturn(transactions);

     
    }

    @Test
    public void testSubmitForm_ValidCredentials() throws Exception {
        Profil profil = new Profil();
        profil.setAdresseMail("user@example.com");
        profil.setPassword("password");
        when(profilRepository.findByAdresseMail("user@example.com")).thenReturn(profil);

        mockMvc.perform(post("/submitForm")
                        .param("email", "user@example.com")
                        .param("password", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    public void testSubmitForm_InvalidCredentials() throws Exception {
        when(profilRepository.findByAdresseMail("user@example.com")).thenReturn(null);

        mockMvc.perform(post("/submitForm")
                        .param("email", "user@example.com")
                        .param("password", "wrongpassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"));
    }

    @Test
    public void testSendMoney_Success() throws Exception {
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("userEmail")).thenReturn("sender@example.com");

        Profil sender = new Profil();
        sender.setAdresseMail("sender@example.com");
        Profil recipient = new Profil();
        recipient.setAdresseMail("recipient@example.com");

        when(profilRepository.findByAdresseMail("sender@example.com")).thenReturn(sender);
        when(profilRepository.findByAdresseMail("recipient@example.com")).thenReturn(recipient);

        // Exécution de la requête
        mockMvc.perform(post("/sendMoney")
                        .sessionAttr("userEmail", "sender@example.com")
                        .param("email", "recipient@example.com")
                        .param("amount", "100.0")
                        .param("description", "Test transaction"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

        // Vérification que la méthode transferMoney est bien appelée
        verify(moneyTransferService, times(1)).transferMoney(sender, recipient, 100.0f, "Test transaction");
    }

    
}