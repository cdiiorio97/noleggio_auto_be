package com.example.noleggioautobe.services;

import com.example.noleggioautobe.dto.DtoPrenotazione;
import com.example.noleggioautobe.entities.Prenotazione;
import com.example.noleggioautobe.repositories.PrenotazioneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    public List<DtoPrenotazione> trovaPrenotazioni(){
        List<Prenotazione> prenotazioni = prenotazioneRepository.findAll();
        List<DtoPrenotazione> dtoPrenotazioniList = new ArrayList<>();
        if(prenotazioni.isEmpty())
            log.warn("Nessuna prenotazione trovata");
        for(Prenotazione p : prenotazioni){
            dtoPrenotazioniList.add(new DtoPrenotazione(p));
        }
        return dtoPrenotazioniList;
    }

    public DtoPrenotazione getPrenotazioneById(Integer id) {
        Prenotazione p = prenotazioneRepository.findById(id).orElse(null);
        if(p == null)
            log.error("Prenotazione non trovata");
        return p != null ? new DtoPrenotazione(p) : null;
    }

    public List<DtoPrenotazione> getPrenotazioneByUserId(Integer id) {
        List<Prenotazione> p = prenotazioneRepository.findByUtenteId(id).orElse(null);
        List<DtoPrenotazione> dtoPrenotazioniList = new ArrayList<>();
        if(p == null)
            log.error("Nessuna prenotazione non trovata per l'utente");
        else {
            for (Prenotazione elem : p) {
                dtoPrenotazioniList.add(new DtoPrenotazione(elem));
            }
        }
        return dtoPrenotazioniList;
    }

    public List<DtoPrenotazione> trovaRichiestePrenotazioni(){
        List<Prenotazione> richieste = prenotazioneRepository.findByConfermataAndCancellata().orElse(null);
        List<DtoPrenotazione> dtoRichiesteList = new ArrayList<>();
        if(richieste == null)
            log.warn("Nessuna richiesta trovata");
        else {
            for (Prenotazione p : richieste) {
                dtoRichiesteList.add(new DtoPrenotazione(p));
            }
        }
        return dtoRichiesteList;
    }
}
