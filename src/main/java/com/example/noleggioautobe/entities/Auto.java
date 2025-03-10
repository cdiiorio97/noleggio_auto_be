package com.example.noleggioautobe.entities;

import lombok.Data;

import jakarta.persistence.*;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@ToString(exclude = "prenotazioni")
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String brand;
    private String modello;
    private String targa;

    @OneToMany(mappedBy = "auto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prenotazione> prenotazioni;
}
