package Controllers;

import DTO.DtoUtente;
import Services.UtenteService;
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
@RequestMapping("/utenti")
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
        DtoUtente utente = utenteService.getUtenteById(id);
        return ResponseEntity.ok(utente);
    }
}
