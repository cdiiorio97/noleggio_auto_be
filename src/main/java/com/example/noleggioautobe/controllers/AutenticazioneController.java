package com.example.noleggioautobe.controllers;

import com.example.noleggioautobe.auth.JwtUtil;
import com.example.noleggioautobe.entities.Utente;
import com.example.noleggioautobe.repositories.UtenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/auth")
public class AutenticazioneController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UtenteRepository utenteRepository;

    public AutenticazioneController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                                    UtenteRepository utenteRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.utenteRepository = utenteRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            String email = authentication.getName();
            Utente user = utenteRepository.findByEmail(email).orElse(null);
            if (user == null) {
                throw new Exception("Invalid username or password");
            }
            String token = jwtUtil.createToken(user);
            return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        try {
            SecurityContextHolder.clearContext();
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
