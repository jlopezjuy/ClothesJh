package com.anelsoftware.repository;

import com.anelsoftware.domain.Modelo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Modelo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModeloRepository extends JpaRepository<Modelo,Long> {

}
