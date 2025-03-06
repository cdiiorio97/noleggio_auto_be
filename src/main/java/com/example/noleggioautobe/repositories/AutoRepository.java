package com.example.noleggioautobe.repositories;

import com.example.noleggioautobe.entities.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoRepository extends JpaRepository<Auto, Integer> {

    @Query(value = "select * from auto where id > 1", nativeQuery = true)
    List<Auto> trovaAutoValide();
}
