package com.example.noleggioautobe.controllers;

import com.example.noleggioautobe.dto.DtoAuto;
import com.example.noleggioautobe.services.AutoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/auto")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AutoController {
    
    @Autowired
    private AutoService autoService;

    @GetMapping("/all/get-all")
    public ResponseEntity getAuto() {
        try {
            List<DtoAuto> auto = autoService.trovaAuto();
            return ResponseEntity.ok(auto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/all/get-by-id")
    public ResponseEntity getAutoById(@RequestParam Integer id) {
        try{
            DtoAuto auto = autoService.trovaAutoById(id);
            return ResponseEntity.ok(auto);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/admin/aggiungi-auto")
    public ResponseEntity aggiungiAuto(@RequestBody DtoAuto dtoAuto){
        try{
            autoService.aggiungiAuto(dtoAuto);
            return ResponseEntity.ok("Auto inserita correttamente");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'inserimento della nuova auto: " + e.getMessage());
        }
    }

    @PutMapping("/admin/modifica-auto")
    public ResponseEntity modificaAuto(@RequestBody DtoAuto dtoAuto){
        try{
            autoService.modificaAuto(dtoAuto);
            return ResponseEntity.ok("Auto modificata correttamente");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante la modifica dell'auto: " + e.getMessage());
        }
    }

    @DeleteMapping("/admin/elimina-auto")
    public ResponseEntity eliminaAuto(@RequestParam Integer id){
        try{
            autoService.eliminaAuto(id);
            return ResponseEntity.ok("Auto eliminata correttamente");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'eliminazione dell'auto: " + e.getMessage());
        }
    }
}
