package com.example.noleggioautobe.repositories;

import com.example.noleggioautobe.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Integer> {
}
