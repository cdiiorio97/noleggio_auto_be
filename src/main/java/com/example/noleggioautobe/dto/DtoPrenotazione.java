package com.example.noleggioautobe.dto;

import com.example.noleggioautobe.entities.Prenotazione;
import lombok.Data;

import java.util.Date;

@Data
public class DtoPrenotazione {

    private Integer id;
    private DtoUtente utente;
    private DtoAuto auto;
    private Date dataRichiesta;
    private Date dataInizio;
    private Date dataFine;
    private Date dataConferma;
    private DtoUtente confermataDa;
    private Boolean confermata;
    private Boolean rifiutata;
    private DtoUtente rifiutataDa;
    private Date dataRifiuto;

    public  DtoPrenotazione(){}

    public DtoPrenotazione(Prenotazione prenotazione) {
        this.id = prenotazione.getId();
        this.utente = new DtoUtente(prenotazione.getUtente());
        this.auto = new DtoAuto(prenotazione.getAuto());
        this.dataRichiesta = prenotazione.getDataRichiesta();
        this.dataInizio = prenotazione.getDataInizio();
        this.dataFine = prenotazione.getDataFine();
        this.dataConferma = prenotazione.getDataConferma();
        this.confermata = prenotazione.getConfermata();
        this.confermataDa = new DtoUtente(prenotazione.getConfermataDa());
        this.rifiutata = prenotazione.getRifiutata();
        this.rifiutataDa = new DtoUtente(prenotazione.getRifiutataDa());
        this.dataRifiuto = prenotazione.getDataRifiuto();
    }
}
