package com.example.noleggioautobe.controllers;

import com.example.noleggioautobe.auth.JwtUtil;
import com.example.noleggioautobe.dto.DtoLoginRequest;
import com.example.noleggioautobe.dto.DtoLoginResponse;
import com.example.noleggioautobe.entities.Utente;
import com.example.noleggioautobe.repositories.UtenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/auth")
public class AutenticazioneController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UtenteRepository utenteRepository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody DtoLoginRequest loginRequest) {
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            String email = authentication.getName();
            Utente user = utenteRepository.findByEmail(email).orElse(null);
            if (user == null) {
                throw new Exception("Invalid username or password");
            }
            String token = jwtUtil.createToken(user);
            DtoLoginResponse loginRes = new DtoLoginResponse(email, token);

            return ResponseEntity.ok(loginRes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
