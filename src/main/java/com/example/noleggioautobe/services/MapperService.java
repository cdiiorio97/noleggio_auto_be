package com.example.noleggioautobe.services;

import com.example.noleggioautobe.dto.*;
import com.example.noleggioautobe.entities.Auto;
import com.example.noleggioautobe.entities.Prenotazione;
import com.example.noleggioautobe.entities.Utente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class MapperService {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public void convertDtoToUtenteModificato(Utente utente, DtoUtenteModifica dto) {
        if(dto.getId() != null && dto.getId() > 0)
            utente.setId(dto.getId());
        utente.setNome(dto.getNome());
        utente.setCognome(dto.getCognome());
        utente.setEmail(dto.getEmail());
        if(dto.getNewPassword() != null && !dto.getNewPassword().isEmpty())
            utente.setPassword(encoder.encode(dto.getNewPassword()));
        utente.setIsAdmin(dto.getIsAdmin());
    }

    public Auto convertDtoToAuto(Auto auto, DtoAuto dto) {
        if (dto.getId() != null && dto.getId() > 0)
            auto.setId(dto.getId());
        auto.setTarga(dto.getTarga());
        auto.setModello(dto.getModello());
        auto.setBrand(dto.getBrand());
        return auto;
    }

    public void convertiDtoModificaPrenotazione(DtoPrenotazione dto, Prenotazione prenotazioneDB) {
        prenotazioneDB.setAuto(dto.getAuto() != null ? convertDtoToAuto(new Auto(), dto.getAuto()) : prenotazioneDB.getAuto());
        prenotazioneDB.setDataInizio(dto.getDataInizio() != null ? dto.getDataInizio() : prenotazioneDB.getDataInizio());
        prenotazioneDB.setDataFine(dto.getDataFine() != null ? dto.getDataFine() : prenotazioneDB.getDataFine());
        prenotazioneDB.setRifiutataDa(null);
        prenotazioneDB.setRifiutata(null);
        prenotazioneDB.setDataRifiuto(null);
        prenotazioneDB.setConfermata(null);
        prenotazioneDB.setConfermataDa(null);
        prenotazioneDB.setDataConferma(null);
    }

    public Prenotazione convertiRichiestaPrenotazione(DtoRichiestaPrenotazione dto, Utente utente) throws Exception {
        SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd");
        Prenotazione pren = new Prenotazione();
        try{
            pren.setAuto(createAutoWithId(dto.getId()));
            pren.setDataRichiesta(new Date());
            pren.setDataInizio(formatoData.parse(dto.getDataInizio()));
            pren.setDataFine(formatoData.parse(dto.getDataFine()));
            pren.setUtente(utente);
            return pren;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Auto createAutoWithId(Integer id) {
        Auto auto = new Auto();
        auto.setId(id);
        return auto;
    }
}
