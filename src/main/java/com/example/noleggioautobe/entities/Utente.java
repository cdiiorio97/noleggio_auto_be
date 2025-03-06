package com.example.noleggioautobe.entities;

import lombok.Data;

import jakarta.persistence.*;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@ToString(exclude = "prenotazioni")
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String cognome;
    private String email;
    private String password;
    private Boolean isAdmin;

    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prenotazione> prenotazioni;

    public Utente(){}
    public Utente(Integer id) {
        this.id = id;
    }

}
