package com.example.noleggioautobe.repositories;

import com.example.noleggioautobe.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Integer> {

    Optional<List<Prenotazione>> findByUtenteIdOrderByIdDesc(int utenteId) ;

    @Query(value = "SELECT * FROM prenotazione " +
            "WHERE (confermata IS NULL) " +
            "AND (rifiutata IS NULL) " +
            "ORDER BY id desc", nativeQuery = true)
    Optional<List<Prenotazione>> trovaRichiestePrenotazioni();

    @Query(value = "SELECT * FROM prenotazione " +
            "WHERE (confermata = true) " +
            "OR (rifiutata = true) " +
            "ORDER BY id desc", nativeQuery = true)
    Optional<List<Prenotazione>> trovaPrenotazioniControllate();
}
