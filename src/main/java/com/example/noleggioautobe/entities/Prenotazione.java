package com.example.noleggioautobe.entities;

import lombok.Data;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "prenotazione")
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
    private Date dataCancellazione;

    @ManyToOne
    @JoinColumn(name = "confermata_da_id")
    private Utente confermataDa;

    private Boolean confermata;
    private Boolean cancellata;

    @ManyToOne
    @JoinColumn(name = "cancellata_da_id")
    private Utente cancellataDa;

}
