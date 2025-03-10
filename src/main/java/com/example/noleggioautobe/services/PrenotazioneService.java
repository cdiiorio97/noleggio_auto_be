package com.example.noleggioautobe.services;

import com.example.noleggioautobe.dto.DtoPrenotazione;
import com.example.noleggioautobe.dto.DtoRichiestaPrenotazione;
import com.example.noleggioautobe.entities.Prenotazione;
import com.example.noleggioautobe.entities.Utente;
import com.example.noleggioautobe.repositories.PrenotazioneRepository;
import com.example.noleggioautobe.repositories.UtenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;
    private final UtenteRepository utenteRepository;
    private final MapperService mapper;

    public PrenotazioneService(PrenotazioneRepository prenotazioneRepository,UtenteRepository utenteRepository,MapperService mapper) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.utenteRepository = utenteRepository;
        this.mapper = mapper;
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

    public DtoPrenotazione getPrenotazioneById(Integer id) throws NullPointerException {
        Prenotazione p = prenotazioneRepository.findById(id).orElseThrow(()-> new NullPointerException("Prenotazione non trovata"));
        return new DtoPrenotazione(p);
    }

    public List<DtoPrenotazione> getPrenotazioneByUserId(Integer id) {
        List<Prenotazione> p = prenotazioneRepository.findByUtenteIdOrderByIdDesc(id);
        List<DtoPrenotazione> dtoPrenotazioniList = new ArrayList<>();
        if(p.isEmpty())
            log.warn("Nessuna prenotazione non trovata per l'utente con id {}", id);
        else
            p.forEach(pr -> dtoPrenotazioniList.add(new DtoPrenotazione(pr)));

        return dtoPrenotazioniList;
    }

    public List<DtoPrenotazione> getPrenotazioneByUserEmail(String email) {
        List<Prenotazione> p = prenotazioneRepository.findByUtenteEmailOrderByIdDesc(email);
        List<DtoPrenotazione> dtoPrenotazioniList = new ArrayList<>();
        if(p.isEmpty())
            log.warn("Nessuna prenotazione non trovata per l'utente con email {} ", email);
        else
            p.forEach(pr -> dtoPrenotazioniList.add(new DtoPrenotazione(pr)));
        return dtoPrenotazioniList;
    }

    public List<DtoPrenotazione> trovaRichiestePrenotazioni(){
        List<Prenotazione> richieste = prenotazioneRepository.trovaRichiestePrenotazioni();
        List<DtoPrenotazione> dtoRichiesteList = new ArrayList<>();
        if(richieste.isEmpty())
            log.warn("Nessuna richiesta trovata");
        else
            richieste.forEach(pr -> dtoRichiesteList.add(new DtoPrenotazione(pr)));
        return dtoRichiesteList;
    }

    public void modificaPrenotazione(DtoPrenotazione dto) throws Exception {
        Prenotazione pren = prenotazioneRepository.findById(dto.getId()).orElseThrow(() -> new NullPointerException("Prenotazione non trovata"));
        mapper.convertiDtoModificaPrenotazione(dto, pren);
        try{
            Boolean autoDisponibile = verificaDisponibilitaAuto(pren.getAuto().getId(), pren.getDataInizio(), pren.getDataFine());
            if(autoDisponibile)
                prenotazioneRepository.save(pren);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void aggiungiRichiestaPrenotazione(DtoRichiestaPrenotazione dto) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Utente utente = utenteRepository.findByEmail(userDetails.getUsername()).orElseThrow(()-> new NullPointerException("Utente non trovato"));
        Prenotazione pren = mapper.convertiRichiestaPrenotazione(dto, utente);
        try{
            Boolean autoDisponibile = verificaDisponibilitaAuto(pren.getAuto().getId(), pren.getDataInizio(), pren.getDataFine());
            if(autoDisponibile)
                prenotazioneRepository.save(pren);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void eliminaPrenotazione(Integer id) throws Exception{
        Prenotazione pren = prenotazioneRepository.findById(id).orElseThrow(()-> new NullPointerException("Prenotazione non trovata"));
        try{
            prenotazioneRepository.deleteById(pren.getId());
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Boolean verificaDisponibilitaAuto(Integer autoId, Date dataInizio, Date dataFine) throws Exception{
        List<Prenotazione> prenotazioniEsistenti = prenotazioneRepository.verificaDisponibilitaAuto(autoId, dataInizio, dataFine);
        if(!prenotazioniEsistenti.isEmpty())
            throw new Exception("Auto già occupata in quel periodo");
        return true;
    }

    public void modificaStatoPrenotazione(Integer id, String azione) throws Exception {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Utente utente = utenteRepository.findByEmail(userDetails.getUsername()).orElseThrow(()-> new NullPointerException("Utente non trovato"));
            Prenotazione prenotazione = prenotazioneRepository.findById(id).orElseThrow(()-> new NullPointerException("Prenotazione non trovata"));
            if (azione.equals("rifiuta")) {
                prenotazione.setRifiutata(true);
                prenotazione.setDataRifiuto(new Date());
                prenotazione.setRifiutataDa(utente);
            }
            else if (azione.equals("conferma")) {
                prenotazione.setConfermata(true);
                prenotazione.setDataConferma(new Date());
                prenotazione.setConfermataDa(utente);
            }
            prenotazioneRepository.save(prenotazione);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
