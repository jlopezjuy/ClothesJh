package com.anelsoftware.repository;

import com.anelsoftware.domain.DetalleFactPres;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DetalleFactPres entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetalleFactPresRepository extends JpaRepository<DetalleFactPres,Long> {
    
}
