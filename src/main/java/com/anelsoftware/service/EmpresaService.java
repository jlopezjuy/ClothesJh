package com.anelsoftware.service;

import com.anelsoftware.service.dto.EmpresaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Empresa.
 */
public interface EmpresaService {

    /**
     * Save a empresa.
     *
     * @param empresaDTO the entity to save
     * @return the persisted entity
     */
    EmpresaDTO save(EmpresaDTO empresaDTO);

    /**
     *  Get all the empresas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EmpresaDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" empresa.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EmpresaDTO findOne(Long id);

    /**
     *  Delete the "id" empresa.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
