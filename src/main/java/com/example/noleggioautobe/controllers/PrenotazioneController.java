package com.example.noleggioautobe.controllers;

import com.example.noleggioautobe.dto.DtoPrenotazione;
import com.example.noleggioautobe.services.PrenotazioneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @GetMapping("/get-all")
    public ResponseEntity getPrenotazioni(){
        List<DtoPrenotazione> dtoPrenotazioni = prenotazioneService.trovaPrenotazioni();
        return ResponseEntity.ok(dtoPrenotazioni);
    }

    @GetMapping("/get-by-id")
    public ResponseEntity getPrenotazioneById(@RequestParam Integer id){
        DtoPrenotazione dtoPrenotazione = prenotazioneService.getPrenotazioneById(id);
        return ResponseEntity.ok(dtoPrenotazione);
    }

    @GetMapping("/get-by-user-id")
    public ResponseEntity getPrenotazioneByUserId(@RequestParam Integer id){
        List<DtoPrenotazione> dtoPrenotazione = prenotazioneService.getPrenotazioneByUserId(id);
        return ResponseEntity.ok(dtoPrenotazione);
    }

    @GetMapping("/richieste-prenotazioni")
    public ResponseEntity richiestePrenotazioni(){
        List<DtoPrenotazione> dtoRichieste = prenotazioneService.trovaRichiestePrenotazioni();
        return ResponseEntity.ok(dtoRichieste);
    }
}
