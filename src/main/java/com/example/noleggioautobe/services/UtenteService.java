package com.example.noleggioautobe.services;

import com.example.noleggioautobe.dto.DtoUtente;
import com.example.noleggioautobe.entities.Utente;
import com.example.noleggioautobe.repositories.UtenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    public List<DtoUtente> getUtenti() {
        List<Utente> utenti = utenteRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        List<DtoUtente> dtoUtenti = new ArrayList<>();
        if(utenti.isEmpty())
            log.warn("nessun utente trovato");
        for (Utente utente : utenti) {
            if(utente.getId() != 1)
                dtoUtenti.add(new DtoUtente(utente));
        }
        return dtoUtenti;
    }

    public DtoUtente getUtenteById(Integer id) throws Exception{
        Utente utente = utenteRepository.findById(id).orElse(null);
        if (utente == null) {
            log.error("Utente non trovato");
            throw new Exception("Utente non trovato");
        }
        return new DtoUtente(utente);
    }

    public void eliminaUtente(Integer id) throws Exception{
        Utente pren = utenteRepository.findById(id).orElse(null);
        if(pren == null)
            throw new Exception("Utente non trovato");
        try{
            utenteRepository.deleteById(id);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void aggiungiUtente(DtoUtente dto) throws Exception {
        Utente Utente = convertiDtoUtente(dto);
        try{
            utenteRepository.save(Utente);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void modificaUtente(DtoUtente dto) throws Exception {
        Utente Utente = convertiDtoUtente(dto);
        try{
            utenteRepository.save(Utente);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    static Utente convertiDtoUtente(DtoUtente dto) {
        Utente utente = new Utente();
        if(dto.getId() != null && dto.getId() != 0)
            utente.setId(dto.getId());
        utente.setNome(dto.getNome());
        utente.setCognome(dto.getCognome());
        utente.setEmail(dto.getEmail());
        utente.setPassword(dto.getPassword());
        utente.setIsAdmin(dto.getIsAdmin());
        return utente;
    }

    public DtoUtente findDtoUtenteByEmail(String email) throws Exception {
        Utente utente = utenteRepository.findByEmail(email).orElse(null);
        if(utente==null)
            throw new Exception("utente non trovato");
        return new DtoUtente(utente);
    }

    public Utente findUtenteByEmail(String email) throws Exception {
        Utente utente = utenteRepository.findByEmail(email).orElse(null);
        if(utente==null)
            throw new Exception("utente non trovato");
        return utente;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Utente u){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if(u.getIsAdmin())
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return authorities;
    }
}
