package com.example.noleggioautobe.controllers;

import com.example.noleggioautobe.dto.DtoUtente;
import com.example.noleggioautobe.dto.DtoUtenteModifica;
import com.example.noleggioautobe.services.UtenteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/utenti")
public class UtenteController {

    private final UtenteService utenteService;
    
    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping("/admin/get-all")
    public ResponseEntity<?> getUtenti() {
        List<DtoUtente> utenti = utenteService.getUtenti();
        return ResponseEntity.ok(utenti);
    }

    @GetMapping("/all/get-by-id")
    public ResponseEntity<?> getUtenteById(@RequestParam Integer id) throws NullPointerException {
        DtoUtente utente = utenteService.getUtenteById(id);
        return ResponseEntity.ok(utente);
    }

    @GetMapping("/all/get-by-email")
    public ResponseEntity<?> getUtenteByEmail(@RequestParam String email) throws NullPointerException {
        DtoUtente utente = utenteService.findDtoUtenteByEmail(email);
        return ResponseEntity.ok(utente);
    }

    @PostMapping("/admin/aggiungi-utente")
    public ResponseEntity<?> aggiungiUtente(@RequestBody DtoUtenteModifica dtoUtente) throws Exception {
        Integer id = utenteService.aggiungiUtente(dtoUtente);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @PutMapping("/all/modifica-utente")
    public ResponseEntity<?> modificaUtente(@RequestBody DtoUtenteModifica dtoUtente) throws NullPointerException, IOException {
        utenteService.modificaUtente(dtoUtente);
        return ResponseEntity.status(HttpStatus.OK).body(dtoUtente.getId());
    }

    @DeleteMapping("/admin/elimina-utente")
    public ResponseEntity<?> eliminaUtente(@RequestParam Integer id) throws Exception {
        utenteService.eliminaUtente(id);
        return ResponseEntity.ok("Utente eliminato correttamente");
    }
}
