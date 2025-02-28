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

    Optional<List<Prenotazione>> findByUtenteId(int utenteId) ;

    @Query(value = "SELECT * FROM prenotazione " +
                    "WHERE (confermata IS NULL OR confermata = false) " +
                    "AND (cancellata IS NULL OR cancellata = false)", nativeQuery = true)
    Optional<List<Prenotazione>> findByConfermataAndCancellata();
}
