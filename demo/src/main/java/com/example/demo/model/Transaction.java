package com.example.demo.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Profil expediteur;

    private String adresseMailDestinataire; // Garder l'adresse mail du destinataire
    private float amount;
    private String description;

    // Constructeurs
    public Transaction() {
    }

    public Transaction(Profil expediteur, String adresseMailDestinataire, float amount, String description) {
        this.expediteur = expediteur;
        this.adresseMailDestinataire = adresseMailDestinataire;
        this.amount = amount;
        this.description = description;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Profil getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(Profil expediteur) {
        this.expediteur = expediteur;
    }

    public String getAdresseMailDestinataire() {
        return adresseMailDestinataire;
    }

    public void setAdresseMailDestinataire(String adresseMailDestinataire) {
        this.adresseMailDestinataire = adresseMailDestinataire;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // toString pour l'affichage
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", expediteur=" + expediteur +
                ", adresseMailDestinataire='" + adresseMailDestinataire + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Float.compare(that.amount, amount) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(expediteur, that.expediteur) &&
                Objects.equals(adresseMailDestinataire, that.adresseMailDestinataire) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, expediteur, adresseMailDestinataire, amount, description);
    }
}