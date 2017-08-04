package com.anelsoftware.repository;

import com.anelsoftware.domain.DetalleFactPres;
import com.anelsoftware.domain.FacturaPresupuesto;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the DetalleFactPres entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetalleFactPresRepository extends JpaRepository<DetalleFactPres,Long> {

    List<DetalleFactPres> findAllByFacturaPresupuesto(FacturaPresupuesto facturaPresupuesto);
}
