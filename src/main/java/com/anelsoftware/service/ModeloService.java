package com.anelsoftware.service;

import com.anelsoftware.service.dto.ModeloDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Modelo.
 */
public interface ModeloService {

    /**
     * Save a modelo.
     *
     * @param modeloDTO the entity to save
     * @return the persisted entity
     */
    ModeloDTO save(ModeloDTO modeloDTO);

    /**
     *  Get all the modelos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ModeloDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" modelo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ModeloDTO findOne(Long id);

    /**
     *  Delete the "id" modelo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
