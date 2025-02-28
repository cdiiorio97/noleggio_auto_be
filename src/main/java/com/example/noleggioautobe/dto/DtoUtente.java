package com.example.noleggioautobe.dto;

import com.example.noleggioautobe.entities.Utente;
import lombok.Data;

@Data
public class DtoUtente {
    private Integer id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private Boolean isAdmin;

    public DtoUtente() {}

    public DtoUtente(Utente utente) {
        if(utente != null) {
            this.id = utente.getId();
            this.nome = utente.getNome();
            this.cognome = utente.getCognome();
            this.email = utente.getEmail();
            this.password = utente.getPassword();
            this.isAdmin = utente.getIsAdmin();
        }
    }
}
