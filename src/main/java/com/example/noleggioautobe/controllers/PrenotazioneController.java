package com.example.noleggioautobe.controllers;

import com.example.noleggioautobe.dto.DtoPrenotazione;
import com.example.noleggioautobe.dto.DtoRichiestaPrenotazione;
import com.example.noleggioautobe.services.PrenotazioneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    public PrenotazioneController(PrenotazioneService prenotazioneService) {
        this.prenotazioneService = prenotazioneService;
    }

    @GetMapping("/admin/get-all")
    public ResponseEntity<?> getPrenotazioniControllate(){
            List<DtoPrenotazione> dtoPrenotazioni = prenotazioneService.trovaPrenotazioniControllate();
            return new ResponseEntity<>(dtoPrenotazioni, HttpStatus.OK);
    }

    @GetMapping("/all/get-by-id")
    public ResponseEntity<?> getPrenotazioneById(@RequestParam Integer id){
        DtoPrenotazione dtoPrenotazione = prenotazioneService.getPrenotazioneById(id);
        return ResponseEntity.ok(dtoPrenotazione);
    }

    @GetMapping("/all/get-by-user-id")
    public ResponseEntity<?> getPrenotazioneByUserId(@RequestParam Integer id){
        List<DtoPrenotazione> dtoPrenotazione = prenotazioneService.getPrenotazioneByUserId(id);
        return ResponseEntity.ok(dtoPrenotazione);
    }

    @GetMapping("/all/get-by-user-email")
    public ResponseEntity<?> getPrenotazioneByUserEmail(@RequestParam String email){
        List<DtoPrenotazione> dtoPrenotazione = prenotazioneService.getPrenotazioneByUserEmail(email);
        return ResponseEntity.ok(dtoPrenotazione);
    }

    @PostMapping("/user/aggiungi-richiesta-prenotazione")
    public ResponseEntity<?> aggiungiRichiestaPrenotazione(@RequestBody DtoRichiestaPrenotazione dtoRichiesta) throws Exception{
        prenotazioneService.aggiungiRichiestaPrenotazione(dtoRichiesta);
        return ResponseEntity.ok("Richiesta prenotazione inserita correttamente");
    }

    @PutMapping("/user/modifica-prenotazione")
    public ResponseEntity<?> modificaPrenotazione(@RequestBody DtoPrenotazione dtoPrenotazione) throws Exception{
        prenotazioneService.modificaPrenotazione(dtoPrenotazione);
        return ResponseEntity.ok("Modifica prenotazione avvenuta correttamente");
    }

    @DeleteMapping("/all/elimina-prenotazione")
    public ResponseEntity<?> eliminaPrenotazione(@RequestParam Integer id) throws Exception{
        prenotazioneService.eliminaPrenotazione(id);
        return ResponseEntity.ok("Prenotazione eliminata correttamente");
    }

    @GetMapping("/admin/get-richieste-prenotazioni")
    public ResponseEntity<?> richiestePrenotazioni(){
        List<DtoPrenotazione> dtoRichieste = prenotazioneService.trovaRichiestePrenotazioni();
        return new ResponseEntity<>(dtoRichieste, HttpStatus.OK);
    }

    @PutMapping("/admin/conferma-prenotazione")
    public ResponseEntity<?> accettaPrenotazione(@RequestParam Integer id) throws Exception{
        prenotazioneService.modificaStatoPrenotazione(id, "conferma");
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("Richiesta prenotazione inserita correttamente");
    }

    @PutMapping("/admin/rifiuta-prenotazione")
    public ResponseEntity<?> rifiutaPrenotazione(@RequestParam Integer id) throws Exception{
        prenotazioneService.modificaStatoPrenotazione(id, "rifiuta");
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("Richiesta prenotazione inserita correttamente");
    }
}
