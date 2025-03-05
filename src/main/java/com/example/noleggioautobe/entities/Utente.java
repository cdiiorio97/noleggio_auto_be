package com.example.noleggioautobe.entities;

import lombok.Data;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Data
@Table(name = "utente")
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String cognome;
    private String email;
    private String password;
    private Boolean isAdmin;

    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL)
    private List<Prenotazione> prenotazioni;

    @Override
    public String toString() {
        return "Utente{id='" + id + "', nome='" + nome + "', cognome='" + cognome + "', email='" + email + "', isAdmin=" + isAdmin + "}";
    }

}
