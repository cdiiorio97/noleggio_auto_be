package com.example.noleggioautobe.entities;

import lombok.Data;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Data
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "auto_id")
    private Auto auto;

    private Date dataRichiesta;
    private Date dataInizio;
    private Date dataFine;
    private Date dataConferma;
    private Date dataRifiuto;

    private Boolean confermata;

    @ManyToOne
    @JoinColumn(name = "confermata_da_id")
    private Utente confermataDa;

    private Boolean rifiutata;

    @ManyToOne
    @JoinColumn(name = "rifiutata_da_id")
    private Utente rifiutataDa;


}
