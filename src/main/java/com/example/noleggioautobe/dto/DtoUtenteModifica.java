package com.example.noleggioautobe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoUtenteModifica {
    private Integer id;
    private String nome;
    private String cognome;
    private String email;
    private String oldPassword;
    private String newPassword;
    private Boolean isAdmin;
}
