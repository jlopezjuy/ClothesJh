package com.anelsoftware.service.impl;

import com.anelsoftware.service.ProveedorService;
import com.anelsoftware.domain.Proveedor;
import com.anelsoftware.repository.ProveedorRepository;
import com.anelsoftware.repository.search.ProveedorSearchRepository;
import com.anelsoftware.service.dto.ProveedorDTO;
import com.anelsoftware.service.mapper.ProveedorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Proveedor.
 */
@Service
@Transactional
public class ProveedorServiceImpl implements ProveedorService{

    private final Logger log = LoggerFactory.getLogger(ProveedorServiceImpl.class);

    private final ProveedorRepository proveedorRepository;

    private final ProveedorMapper proveedorMapper;

    private final ProveedorSearchRepository proveedorSearchRepository;

    public ProveedorServiceImpl(ProveedorRepository proveedorRepository, ProveedorMapper proveedorMapper, ProveedorSearchRepository proveedorSearchRepository) {
        this.proveedorRepository = proveedorRepository;
        this.proveedorMapper = proveedorMapper;
        this.proveedorSearchRepository = proveedorSearchRepository;
    }

    /**
     * Save a proveedor.
     *
     * @param proveedorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProveedorDTO save(ProveedorDTO proveedorDTO) {
        log.debug("Request to save Proveedor : {}", proveedorDTO);
        Proveedor proveedor = proveedorMapper.toEntity(proveedorDTO);
        proveedor = proveedorRepository.save(proveedor);
        ProveedorDTO result = proveedorMapper.toDto(proveedor);
        proveedorSearchRepository.save(proveedor);
        return result;
    }

    /**
     *  Get all the proveedors.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProveedorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Proveedors");
        return proveedorRepository.findAll(pageable)
            .map(proveedorMapper::toDto);
    }

    /**
     *  Get one proveedor by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProveedorDTO findOne(Long id) {
        log.debug("Request to get Proveedor : {}", id);
        Proveedor proveedor = proveedorRepository.findOne(id);
        return proveedorMapper.toDto(proveedor);
    }

    /**
     *  Delete the  proveedor by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Proveedor : {}", id);
        proveedorRepository.delete(id);
        proveedorSearchRepository.delete(id);
    }

    /**
     * Search for the proveedor corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProveedorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Proveedors for query {}", query);
        Page<Proveedor> result = proveedorSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(proveedorMapper::toDto);
    }
}
