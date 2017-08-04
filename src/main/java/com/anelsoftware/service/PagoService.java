package com.anelsoftware.service;

import com.anelsoftware.service.dto.PagoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Pago.
 */
public interface PagoService {

    /**
     * Save a pago.
     *
     * @param pagoDTO the entity to save
     * @return the persisted entity
     */
    PagoDTO save(PagoDTO pagoDTO);

    /**
     *  Get all the pagos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PagoDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" pago.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PagoDTO findOne(Long id);

    /**
     *  Delete the "id" pago.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     *
     * @param pageable
     * @param encargoId
     * @return
     */
    Page<PagoDTO> findAllByEncargoId(Pageable pageable, Long encargoId);

    /**
     * Search for the pago corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PagoDTO> search(String query, Pageable pageable);
}
