package com.anelsoftware.service;

import com.anelsoftware.service.dto.ModeloEncargoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ModeloEncargo.
 */
public interface ModeloEncargoService {

    /**
     * Save a modeloEncargo.
     *
     * @param modeloEncargoDTO the entity to save
     * @return the persisted entity
     */
    ModeloEncargoDTO save(ModeloEncargoDTO modeloEncargoDTO);

    /**
     *  Get all the modeloEncargos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ModeloEncargoDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" modeloEncargo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ModeloEncargoDTO findOne(Long id);

    /**
     *  Delete the "id" modeloEncargo.
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
    Page<ModeloEncargoDTO> findAllByEncargoId(Pageable pageable, Long encargoId);
}
