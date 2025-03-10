package com.example.noleggioautobe.services;

import com.example.noleggioautobe.dto.DtoUtente;
import com.example.noleggioautobe.dto.DtoUtenteModifica;
import com.example.noleggioautobe.entities.Utente;
import com.example.noleggioautobe.repositories.UtenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UtenteService {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private final UtenteRepository utenteRepository;
    private final MapperService mapper;

    public UtenteService(UtenteRepository utenteRepository, MapperService mapper) {
        this.utenteRepository = utenteRepository;
        this.mapper = mapper;
    }

    public List<DtoUtente> getUtenti() {
        List<Utente> utenti = utenteRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        List<DtoUtente> dtoUtentiList = new ArrayList<>();
        if(utenti.isEmpty())
            log.warn("Nessun utente trovato");
        utenti.forEach(utente -> dtoUtentiList.add(new DtoUtente(utente)));
        return dtoUtentiList;
    }

    public DtoUtente getUtenteById(Integer id) throws NullPointerException{
        Utente utente = utenteRepository.findById(id).orElseThrow(() -> new NullPointerException("Utente non trovato"));
        return new DtoUtente(utente);
    }

    public void eliminaUtente(Integer id) throws Exception{
        Utente utente = utenteRepository.findById(id).orElseThrow(() -> new NullPointerException("Utente non trovato"));
        try{
            utenteRepository.deleteById(utente.getId());
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Integer aggiungiUtente(DtoUtenteModifica dto) throws Exception {
        Utente utente = utenteRepository.findById(dto.getId()).orElse(null);
        if(utente != null)
            throw new Exception("id utente già registrato");
        Utente newUser = new Utente();
        mapper.convertDtoToUtenteModificato(newUser, dto);
        try{
            utenteRepository.save(newUser);
            return newUser.getId();
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void modificaUtente(DtoUtenteModifica dto) throws NullPointerException, IOException {
        try{
            Utente utente = utenteRepository.findById(dto.getId()).orElseThrow(() -> new NullPointerException("Utente non trovato"));
            String oldPwInput = dto.getOldPassword();
            if(oldPwInput != null) {
                if (!encoder.matches(dto.getOldPassword(), utente.getPassword()))
                    throw new IOException("La password vecchia inserita non corrisponde");
            }
            mapper.convertDtoToUtenteModificato(utente, dto);
            utenteRepository.save(utente);
        } catch ( IOException e ){
            throw new IOException(e.getMessage());
        } catch (NullPointerException e){
            throw new NullPointerException(e.getMessage());
        }
    }

    public DtoUtente findDtoUtenteByEmail(String email) throws NullPointerException {
        Utente utente = utenteRepository.findByEmail(email).orElseThrow(() -> new NullPointerException("Utente non trovato"));
        return new DtoUtente(utente);
    }
}
