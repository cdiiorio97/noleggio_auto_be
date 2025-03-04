package com.example.noleggioautobe.controllers;

import com.example.noleggioautobe.dto.DtoPrenotazione;
import com.example.noleggioautobe.services.PrenotazioneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/prenotazioni")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @GetMapping("/admin/get-all")
    public ResponseEntity getPrenotazioniControllate(){
        try {
            List<DtoPrenotazione> dtoPrenotazioni = prenotazioneService.trovaPrenotazioniControllate();
            return new ResponseEntity<>(dtoPrenotazioni, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/all/get-by-id")
    public ResponseEntity getPrenotazioneById(@RequestParam Integer id){
        try {
            DtoPrenotazione dtoPrenotazione = prenotazioneService.getPrenotazioneById(id);
            return ResponseEntity.ok(dtoPrenotazione);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/all/get-by-user-id")
    public ResponseEntity getPrenotazioneByUserId(@RequestParam Integer id){
        try {
            List<DtoPrenotazione> dtoPrenotazione = prenotazioneService.getPrenotazioneByUserId(id);
            return ResponseEntity.ok(dtoPrenotazione);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/all/get-by-user-email")
    public ResponseEntity getPrenotazioneByUserEmail(@RequestParam String email){
        try {
            List<DtoPrenotazione> dtoPrenotazione = prenotazioneService.getPrenotazioneByUserEmail(email);
            return ResponseEntity.ok(dtoPrenotazione);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/user/aggiungi-richiesta-prenotazione")
    public ResponseEntity aggiungiRichiestaPrenotazione(@RequestBody DtoPrenotazione dtoPrenotazione){
        try{
            prenotazioneService.aggiungiRichiestaPrenotazione(dtoPrenotazione);
            return ResponseEntity.ok("Richiesta prenotazione inserita correttamente");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'aggiunta della prenotazione: " + e.getMessage());
        }
    }

    @PutMapping("/user/modifica-prenotazione")
    public ResponseEntity modificaPrenotazione(@RequestBody DtoPrenotazione dtoPrenotazione){
        try{
            prenotazioneService.modificaPrenotazione(dtoPrenotazione);
            return ResponseEntity.ok("Modifica prenotazione avvenuta correttamente");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante la modifica della prenotazione: " + e.getMessage());
        }
    }

    @DeleteMapping("/all/elimina-prenotazione")
    public ResponseEntity eliminaPrenotazione(@RequestParam Integer id){
        try{
            prenotazioneService.eliminaPrenotazione(id);
            return ResponseEntity.ok("Prenotazione eliminata correttamente");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'eliminazione della prenotazione: " + e.getMessage());
        }
    }

    @GetMapping("/admin/get-richieste-prenotazioni")
    public ResponseEntity richiestePrenotazioni(){
        try {
            List<DtoPrenotazione> dtoRichieste = prenotazioneService.trovaRichiestePrenotazioni();
            return new ResponseEntity<>(dtoRichieste, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/admin/conferma-prenotazione")
    public ResponseEntity accettaPrenotazione(@RequestBody DtoPrenotazione dtoPrenotazione){
        try{
            prenotazioneService.confermaPrenotazione(dtoPrenotazione);
            return ResponseEntity.ok("Prenotazione confermata");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante la conferma della prenotazione: " + e.getMessage());
        }
    }

    @PutMapping("/admin/rifiuta-prenotazione")
    public ResponseEntity rifiutaPrenotazione(@RequestBody DtoPrenotazione dtoPrenotazione){
        try{
            prenotazioneService.rifiutaPrenotazione(dtoPrenotazione);
            return ResponseEntity.ok("Prenotazione respinta");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante il rifiuto della prenotazione: " + e.getMessage());
        }
    }
}
