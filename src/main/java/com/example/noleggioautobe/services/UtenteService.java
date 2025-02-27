package com.example.noleggioautobe.services;

import com.example.noleggioautobe.dto.DtoUtente;
import com.example.noleggioautobe.entities.Utente;
import com.example.noleggioautobe.repositories.UtenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    public List<DtoUtente> getUtenti() {
        List<Utente> utenti = utenteRepository.findAll();
        List<DtoUtente> dtoUtenti = new ArrayList<>();
        if(utenti.isEmpty())
            log.warn("nessun utente trovato");
        for (Utente utente : utenti) {
            dtoUtenti.add(new DtoUtente(utente));
        }
        return dtoUtenti;
    }

    public DtoUtente getUtenteById(Integer id) {
        Utente utente = utenteRepository.findById(id).orElse(null);
        if (utente == null)
            log.error("Utente non trovato");
        return utente != null ? new DtoUtente(utente) : null;
    }
}
