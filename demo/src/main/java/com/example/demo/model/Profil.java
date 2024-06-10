package com.example.demo.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "profil")
public class Profil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String adresseMail;


    private String password;

    private float argent;

    // Constructeurs
    public Profil() {
    }

    public Profil(String adresseMail, float argent) {
        this.adresseMail = adresseMail;
        this.argent = argent;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    public float getArgent() {
        return argent;
    }

    public void setArgent(float argent) {
        this.argent = argent;
    }

    // toString pour l'affichage
    @Override
    public String toString() {
        return "Profil{" +
                "id=" + id +
                ", adresseMail='" + adresseMail + '\'' +
                ", argent=" + argent +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profil profil = (Profil) o;
        return id.equals(profil.id) &&
                Objects.equals(adresseMail, profil.adresseMail) &&
                Objects.equals(argent, profil.argent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, adresseMail, argent);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
