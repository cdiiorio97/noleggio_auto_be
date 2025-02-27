package com.example.noleggioautobe.services;

import com.example.noleggioautobe.entities.Utente;
import com.example.noleggioautobe.repositories.UtenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AutenticazioneService {

    @Autowired
    private UtenteRepository utenteRepository;

    public String autenticazione(String username, String password) throws Exception {
        Utente utenteTrovato = utenteRepository.findByNome(username).orElse(null);
        if(utenteTrovato == null)
            throw new Exception("Utente non trovato");
        if(!utenteTrovato.getPassword().equals(password))
            throw new Exception("Password errata");
        return utenteTrovato.getIsAdmin() ? "admin" : "logged";
    }
}
