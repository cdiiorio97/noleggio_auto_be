package com.example.noleggioautobe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DtoLoginResponse {
    private String email;
    private String token;
}
