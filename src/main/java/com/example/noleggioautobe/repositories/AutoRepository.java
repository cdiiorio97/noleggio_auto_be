package com.example.noleggioautobe.repositories;

import com.example.noleggioautobe.entities.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AutoRepository extends JpaRepository<Auto, Integer> {

    @Query(value =  "SELECT a.* FROM AUTO a " +
                    "WHERE a.id NOT IN ( " +
                    "  SELECT p.auto_id FROM PRENOTAZIONE p " +
                    "  WHERE (p.data_inizio BETWEEN :dateStart AND :dateEnd OR p.data_fine BETWEEN :dateStart AND :dateEnd )" +
                    ")", nativeQuery = true)
    List<Auto> trovaAutoDisponibili(@Param("dateStart") Date dateStart,
                                    @Param("dateEnd") Date dateEnd);
}
