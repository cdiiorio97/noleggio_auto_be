package com.example.noleggioautobe.dto;

import com.example.noleggioautobe.entities.Utente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoUtente {
    private Integer id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private Boolean isAdmin;

    public DtoUtente(Utente utente) {
        if(utente != null) {
            this.id = utente.getId();
            this.nome = utente.getNome();
            this.cognome = utente.getCognome();
            this.email = utente.getEmail();
            this.isAdmin = utente.getIsAdmin();
        }
    }

    public DtoUtente(DtoUtenteModifica dto){
        this.id = dto.getId();
        this.nome = dto.getNome();
        this.cognome = dto.getCognome();
        this.email = dto.getEmail();
        this.isAdmin = dto.getIsAdmin();
    }
}
