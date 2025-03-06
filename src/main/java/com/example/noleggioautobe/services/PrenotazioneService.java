package com.example.noleggioautobe.services;

import com.example.noleggioautobe.dto.DtoPrenotazione;
import com.example.noleggioautobe.dto.DtoRichiestaPrenotazione;
import com.example.noleggioautobe.entities.Auto;
import com.example.noleggioautobe.entities.Prenotazione;
import com.example.noleggioautobe.entities.Utente;
import com.example.noleggioautobe.repositories.PrenotazioneRepository;
import com.example.noleggioautobe.repositories.UtenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.noleggioautobe.services.AutoService.convertiDtoAuto;
import static com.example.noleggioautobe.services.UtenteService.convertiDtoUtente;

@Service
@Slf4j
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;
    private final UtenteRepository utenteRepository;

    public PrenotazioneService(PrenotazioneRepository prenotazioneRepository,UtenteRepository utenteRepository) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.utenteRepository = utenteRepository;
    }

    public List<DtoPrenotazione> trovaPrenotazioniControllate(){
        List<Prenotazione> prenotazioni = prenotazioneRepository.trovaPrenotazioniControllate();
        List<DtoPrenotazione> dtoPrenotazioniList = new ArrayList<>();
        if(prenotazioni.isEmpty())
            log.warn("Nessuna prenotazione trovata");
        for(Prenotazione p : prenotazioni){
            dtoPrenotazioniList.add(new DtoPrenotazione(p));
        }
        return dtoPrenotazioniList;
    }

    public DtoPrenotazione getPrenotazioneById(Integer id) throws Exception {
        Prenotazione p = prenotazioneRepository.findById(id).orElse(null);
        if(p == null) {
            log.error("Prenotazione non trovata");
            throw new Exception("Prenotazione non trovata");
        }
        return new DtoPrenotazione(p);
    }

    public List<DtoPrenotazione> getPrenotazioneByUserId(Integer id) {
        List<Prenotazione> p = prenotazioneRepository.findByUtenteIdOrderByIdDesc(id);
        List<DtoPrenotazione> dtoPrenotazioniList = new ArrayList<>();
        if(p.isEmpty())
            log.warn("Nessuna prenotazione non trovata per l'utente");
        else {
            for (Prenotazione elem : p) {
                dtoPrenotazioniList.add(new DtoPrenotazione(elem));
            }
        }
        return dtoPrenotazioniList;
    }

    public List<DtoPrenotazione> getPrenotazioneByUserEmail(String email) {
        List<Prenotazione> p = prenotazioneRepository.findByUtenteEmailOrderByIdDesc(email);
        List<DtoPrenotazione> dtoPrenotazioniList = new ArrayList<>();
        if(p.isEmpty())
            log.warn("Nessuna prenotazione non trovata per l'utente");
        else {
            for (Prenotazione elem : p) {
                dtoPrenotazioniList.add(new DtoPrenotazione(elem));
            }
        }
        return dtoPrenotazioniList;
    }

    public List<DtoPrenotazione> trovaRichiestePrenotazioni(){
        List<Prenotazione> richieste = prenotazioneRepository.trovaRichiestePrenotazioni();
        List<DtoPrenotazione> dtoRichiesteList = new ArrayList<>();
        if(richieste.isEmpty())
            log.warn("Nessuna richiesta trovata");
        else {
            for (Prenotazione p : richieste) {
                dtoRichiesteList.add(new DtoPrenotazione(p));
            }
        }
        return dtoRichiesteList;
    }

    public void modificaPrenotazione(DtoPrenotazione dto) throws Exception {
        Prenotazione prenotazione = prenotazioneRepository.findById(dto.getId()).orElse(null);
        if(prenotazione == null) {
            throw new Exception("Prenotazione non trovata");
        }
        convertiDtoModifica(dto, prenotazione);
        try{
            prenotazioneRepository.save(prenotazione);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void eliminaPrenotazione(Integer id) throws Exception{
        Prenotazione pren = prenotazioneRepository.findById(id).orElse(null);
        if(pren == null)
            throw new Exception("Prenotazione non trovata");
        try{
            prenotazioneRepository.deleteById(id);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void aggiungiRichiestaPrenotazione(DtoRichiestaPrenotazione dto) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Utente utente = utenteRepository.findByEmail(userDetails.getUsername()).orElse(null);
        if(utente == null)
            throw new Exception("Utente non trovato");
        SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd");
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setAuto(new Auto(dto.getId()));
        prenotazione.setDataRichiesta(new Date());
        prenotazione.setDataInizio(formatoData.parse(dto.getDataInizio()));
        prenotazione.setDataFine(formatoData.parse(dto.getDataFine()));
        prenotazione.setUtente(new Utente(utente.getId()));  ///chiedere se ha senso fare così per non mandare dati come la PW in giro
        try{
            prenotazioneRepository.save(prenotazione);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void confermaPrenotazione(Integer id) throws Exception {
        Prenotazione prenotazione = prenotazioneRepository.findById(id).orElse(null);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Utente utente = utenteRepository.findByEmail(userDetails.getUsername()).orElse(null);
        if(utente == null)
            throw new Exception("Utente non trovata");
        if(prenotazione == null)
            throw new Exception("Prenotazione non trovata");
        prenotazione.setConfermata(true);
        prenotazione.setDataConferma(new Date());
        prenotazione.setConfermataDa(utente);
        try{
            prenotazioneRepository.save(prenotazione);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void rifiutaPrenotazione(Integer id) throws Exception {
        Prenotazione prenotazione = prenotazioneRepository.findById(id).orElse(null);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Utente utente = utenteRepository.findByEmail(userDetails.getUsername()).orElse(null);
        if(prenotazione == null)
            throw new Exception("Prenotazione non trovata");
        if(utente == null)
            throw new Exception("Utente non trovata");
        prenotazione.setRifiutata(true);
        prenotazione.setDataRifiuto(new Date());
        prenotazione.setRifiutataDa(utente);
        try{
            prenotazioneRepository.save(prenotazione);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    private Prenotazione convertiDtoPrenotazione(DtoPrenotazione dto) {
        Prenotazione prenotazione = new Prenotazione();
        if(dto.getId() != null  && dto.getId() != 0)
            prenotazione.setId(dto.getId());
        prenotazione.setAuto(convertiDtoAuto(dto.getAuto()));
        prenotazione.setUtente(convertiDtoUtente(dto.getUtente()));
        prenotazione.setDataRichiesta(dto.getDataRichiesta());
        prenotazione.setDataInizio(dto.getDataInizio());
        prenotazione.setDataFine(dto.getDataFine());
        if (dto.getConfermata() != null) {
            prenotazione.setConfermata(dto.getConfermata());
            prenotazione.setConfermataDa(convertiDtoUtente(dto.getConfermataDa()));
            prenotazione.setDataConferma(dto.getDataConferma());
        }
        if (dto.getRifiutata() != null) {
            prenotazione.setRifiutata(dto.getRifiutata());
            prenotazione.setRifiutataDa(convertiDtoUtente(dto.getRifiutataDa()));
            prenotazione.setDataRifiuto(dto.getDataRifiuto());
        }
        return prenotazione;
    }

    private void convertiDtoModifica(DtoPrenotazione dto, Prenotazione prenotazioneDB) {
        prenotazioneDB.setAuto(dto.getAuto() != null ? convertiDtoAuto(dto.getAuto()) : prenotazioneDB.getAuto());
        prenotazioneDB.setUtente(dto.getUtente() != null ? convertiDtoUtente(dto.getUtente()) : prenotazioneDB.getUtente());
        prenotazioneDB.setDataInizio(dto.getDataInizio() != null ? dto.getDataInizio() : prenotazioneDB.getDataInizio());
        prenotazioneDB.setDataFine(dto.getDataFine() != null ? dto.getDataFine() : prenotazioneDB.getDataFine());
        prenotazioneDB.setRifiutataDa(null);
        prenotazioneDB.setRifiutata(null);
        prenotazioneDB.setDataRifiuto(null);
        prenotazioneDB.setConfermata(null);
        prenotazioneDB.setConfermataDa(null);
        prenotazioneDB.setDataConferma(null);
    }

}
