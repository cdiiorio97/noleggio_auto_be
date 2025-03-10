package com.example.noleggioautobe.controllers;

import com.example.noleggioautobe.dto.DtoAuto;
import com.example.noleggioautobe.services.AutoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/auto")
public class AutoController {

    private final AutoService autoService;

    public AutoController(AutoService autoService) { this.autoService = autoService; }

    @GetMapping("/all/get-all")
    public ResponseEntity<?> getAuto() {
        List<DtoAuto> auto = autoService.trovaAuto();
        return ResponseEntity.ok(auto);
    }

    @GetMapping("/all/get-by-id")
    public ResponseEntity<?> getAutoById(@RequestParam Integer id) {
        DtoAuto auto = autoService.trovaAutoById(id);
        return ResponseEntity.ok(auto);
    }

    @PostMapping("/admin/aggiungi-auto")
    public ResponseEntity<?> aggiungiAuto(@RequestBody DtoAuto dtoAuto) throws Exception{
        Integer auto = autoService.aggiungiAuto(dtoAuto);
        return ResponseEntity.status(HttpStatus.OK).body(auto);
    }

    @PutMapping("/admin/modifica-auto")
    public ResponseEntity<?> modificaAuto(@RequestBody DtoAuto dtoAuto) throws Exception {
        autoService.modificaAuto(dtoAuto);
        return ResponseEntity.status(HttpStatus.OK).body(dtoAuto.getId());
    }

    @DeleteMapping("/admin/elimina-auto")
    public ResponseEntity<?> eliminaAuto(@RequestParam Integer id) throws NullPointerException{
        autoService.eliminaAuto(id);
        return ResponseEntity.ok("Auto eliminata correttamente");
    }

    @GetMapping("/all/trova-auto-disponibili")
    public ResponseEntity<?> getAutoDisponibili(@RequestParam("dataInizio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date dataInizio,
                                                @RequestParam("dataFine") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date dataFine) throws Exception {
        List<DtoAuto> autoList = autoService.cercaAutoDisponibili(dataInizio, dataFine);
        return ResponseEntity.ok(autoList);
    }
}
