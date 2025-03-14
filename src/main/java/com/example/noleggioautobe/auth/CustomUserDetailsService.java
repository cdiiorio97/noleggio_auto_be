package com.example.noleggioautobe.auth;

import com.example.noleggioautobe.entities.Utente;
import com.example.noleggioautobe.repositories.UtenteRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtenteRepository utenteRepository;

    public CustomUserDetailsService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {
        Utente user = utenteRepository.findByNome(nome).orElseThrow(() -> new UsernameNotFoundException(nome));
        String role = user.getIsAdmin() ? "ADMIN" : "USER";
        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(role)
                .build();
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        Utente user = utenteRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        String role = user.getIsAdmin() ? "ROLE_ADMIN" : "ROLE_USER";
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

        return new User(user.getEmail(), user.getPassword(), authorities);
    }
}