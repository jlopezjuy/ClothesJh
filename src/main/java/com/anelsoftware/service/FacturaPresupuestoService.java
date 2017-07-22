package com.anelsoftware.service;

import com.anelsoftware.service.dto.FacturaPresupuestoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FacturaPresupuesto.
 */
public interface FacturaPresupuestoService {

    /**
     * Save a facturaPresupuesto.
     *
     * @param facturaPresupuestoDTO the entity to save
     * @return the persisted entity
     */
    FacturaPresupuestoDTO save(FacturaPresupuestoDTO facturaPresupuestoDTO);

    /**
     *  Get all the facturaPresupuestos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FacturaPresupuestoDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" facturaPresupuesto.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FacturaPresupuestoDTO findOne(Long id);

    /**
     *  Delete the "id" facturaPresupuesto.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the facturaPresupuesto corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FacturaPresupuestoDTO> search(String query, Pageable pageable);
}
