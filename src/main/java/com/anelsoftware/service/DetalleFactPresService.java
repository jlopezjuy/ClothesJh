package com.anelsoftware.service;

import com.anelsoftware.service.dto.DetalleFactPresDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing DetalleFactPres.
 */
public interface DetalleFactPresService {

    /**
     * Save a detalleFactPres.
     *
     * @param detalleFactPresDTO the entity to save
     * @return the persisted entity
     */
    DetalleFactPresDTO save(DetalleFactPresDTO detalleFactPresDTO);

    /**
     *  Get all the detalleFactPres.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DetalleFactPresDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" detalleFactPres.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DetalleFactPresDTO findOne(Long id);

    /**
     *  Delete the "id" detalleFactPres.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     *
     * @param pageable
     * @param facturaPresupuestoId
     * @return
     */
    List<DetalleFactPresDTO> findAllByFacturaPresupuestoId(Long facturaPresupuestoId);
}
