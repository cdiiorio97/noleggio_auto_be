package com.example.noleggioautobe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoLoginUserToken {
    private Boolean isAdmin;
    private String email;

    public DtoLoginUserToken(Authentication authentication) {
        this.isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        this.email = authentication.getName();
    }
}
