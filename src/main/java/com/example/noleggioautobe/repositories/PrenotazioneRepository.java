package com.example.noleggioautobe.repositories;

import com.example.noleggioautobe.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Integer> {

    List<Prenotazione> findByUtenteIdOrderByIdDesc(int utenteId) ;
    List<Prenotazione> findByUtenteEmailOrderByIdDesc(String utenteEmail) ;

    @Query(value = "SELECT * FROM prenotazione " +
            "WHERE (confermata IS NULL) " +
            "AND (rifiutata IS NULL) " +
            "ORDER BY id desc", nativeQuery = true)
    List<Prenotazione> trovaRichiestePrenotazioni();

    @Query(value = "SELECT * FROM prenotazione " +
            "WHERE (confermata = true) " +
            "OR (rifiutata = true) " +
            "ORDER BY id desc", nativeQuery = true)
    List<Prenotazione> trovaPrenotazioniControllate();

    @Query(value = "SELECT id FROM PRENOTAZIONE "+
            "WHERE (data_inizio BETWEEN :dateStart AND :dateEnd " +
            "OR data_fine BETWEEN :dateStart AND :dateEnd) " +
            "AND auto_id = :autoId", nativeQuery = true)
    List<Integer> trovaPrenotazioniIntervallo(@Param("autoId") Integer autoId,
                                              @Param("dateStart") Date dateStart,
                                              @Param("dateEnd") Date dateEnd);

}
