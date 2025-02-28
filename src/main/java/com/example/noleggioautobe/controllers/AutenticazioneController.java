package com.example.noleggioautobe.controllers;

import com.example.noleggioautobe.dto.DtoLoginRequest;
import com.example.noleggioautobe.dto.DtoUtente;
import com.example.noleggioautobe.services.AutenticazioneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/auth")
public class AutenticazioneController {

    @Autowired
    private AutenticazioneService autenticazioneService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody DtoLoginRequest loginRequest) {
        try {
            DtoUtente loginAvvenuto = autenticazioneService.autenticazione(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(loginAvvenuto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
