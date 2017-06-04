package com.anelsoftware.service;

import com.anelsoftware.service.dto.EncargoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Encargo.
 */
public interface EncargoService {

    /**
     * Save a encargo.
     *
     * @param encargoDTO the entity to save
     * @return the persisted entity
     */
    EncargoDTO save(EncargoDTO encargoDTO);

    /**
     *  Get all the encargos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EncargoDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" encargo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EncargoDTO findOne(Long id);

    /**
     *  Delete the "id" encargo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
