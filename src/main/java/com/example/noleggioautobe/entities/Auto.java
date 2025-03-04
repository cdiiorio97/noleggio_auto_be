package com.example.noleggioautobe.entities;

import lombok.Data;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "auto")
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String brand;
    private String modello;
    private String targa;

    @OneToMany(mappedBy = "auto", cascade = CascadeType.ALL)
    private List<Prenotazione> prenotazioni;
}
