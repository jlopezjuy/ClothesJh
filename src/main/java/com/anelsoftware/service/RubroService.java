package com.anelsoftware.service;

import com.anelsoftware.service.dto.RubroDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Rubro.
 */
public interface RubroService {

    /**
     * Save a rubro.
     *
     * @param rubroDTO the entity to save
     * @return the persisted entity
     */
    RubroDTO save(RubroDTO rubroDTO);

    /**
     *  Get all the rubros.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RubroDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" rubro.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RubroDTO findOne(Long id);

    /**
     *  Delete the "id" rubro.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the rubro corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RubroDTO> search(String query, Pageable pageable);
}
