package com.example.noleggioautobe.controllers;

import com.example.noleggioautobe.dto.DtoUtente;
import com.example.noleggioautobe.services.UtenteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/utenti")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @GetMapping("/get-all")
    public ResponseEntity getUtenti() {
        List<DtoUtente> utenti = utenteService.getUtenti();
        return ResponseEntity.ok(utenti);
    }

    @GetMapping("/get-by-id")
    public ResponseEntity getUtenteById(@RequestParam Integer id) {
        try{
            DtoUtente utente = utenteService.getUtenteById(id);
            return ResponseEntity.ok(utente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/get-by-email")
    public ResponseEntity getUtenteByEmail(@RequestParam String email) {
        try{
            DtoUtente utente = utenteService.findDtoUtenteByEmail(email);
            return ResponseEntity.ok(utente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/aggiungi-utente")
    public ResponseEntity aggiungiUtente(@RequestBody DtoUtente dtoUtente){
        try{
            utenteService.aggiungiUtente(dtoUtente);
            return ResponseEntity.ok("Utente inserito correttamente");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'inserimento del nuovo utente: " + e.getMessage());
        }
    }

    @PutMapping("/modifica-utente")
    public ResponseEntity modificaUtente(@RequestBody DtoUtente dtoUtente){
        try{
            utenteService.modificaUtente(dtoUtente);
            return ResponseEntity.ok("Utente modificato correttamente");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante la modifica dei dati utente: " + e.getMessage());
        }
    }

    @DeleteMapping("/elimina-utente")
    public ResponseEntity eliminaUtente(@RequestParam Integer id){
        try{
            utenteService.eliminaUtente(id);
            return ResponseEntity.ok("Utente eliminato correttamente");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'eliminazione dell'utente: " + e.getMessage());
        }
    }
}
