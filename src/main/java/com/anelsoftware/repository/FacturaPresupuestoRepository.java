package com.anelsoftware.repository;

import com.anelsoftware.domain.FacturaPresupuesto;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FacturaPresupuesto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacturaPresupuestoRepository extends JpaRepository<FacturaPresupuesto,Long> {
    
}
