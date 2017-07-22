package com.anelsoftware.service.impl;

import com.anelsoftware.service.FacturaPresupuestoService;
import com.anelsoftware.domain.FacturaPresupuesto;
import com.anelsoftware.repository.FacturaPresupuestoRepository;
import com.anelsoftware.repository.search.FacturaPresupuestoSearchRepository;
import com.anelsoftware.service.dto.FacturaPresupuestoDTO;
import com.anelsoftware.service.mapper.FacturaPresupuestoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing FacturaPresupuesto.
 */
@Service
@Transactional
public class FacturaPresupuestoServiceImpl implements FacturaPresupuestoService{

    private final Logger log = LoggerFactory.getLogger(FacturaPresupuestoServiceImpl.class);

    private final FacturaPresupuestoRepository facturaPresupuestoRepository;

    private final FacturaPresupuestoMapper facturaPresupuestoMapper;

    private final FacturaPresupuestoSearchRepository facturaPresupuestoSearchRepository;

    public FacturaPresupuestoServiceImpl(FacturaPresupuestoRepository facturaPresupuestoRepository, FacturaPresupuestoMapper facturaPresupuestoMapper, FacturaPresupuestoSearchRepository facturaPresupuestoSearchRepository) {
        this.facturaPresupuestoRepository = facturaPresupuestoRepository;
        this.facturaPresupuestoMapper = facturaPresupuestoMapper;
        this.facturaPresupuestoSearchRepository = facturaPresupuestoSearchRepository;
    }

    /**
     * Save a facturaPresupuesto.
     *
     * @param facturaPresupuestoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FacturaPresupuestoDTO save(FacturaPresupuestoDTO facturaPresupuestoDTO) {
        log.debug("Request to save FacturaPresupuesto : {}", facturaPresupuestoDTO);
        FacturaPresupuesto facturaPresupuesto = facturaPresupuestoMapper.toEntity(facturaPresupuestoDTO);
        facturaPresupuesto = facturaPresupuestoRepository.save(facturaPresupuesto);
        FacturaPresupuestoDTO result = facturaPresupuestoMapper.toDto(facturaPresupuesto);
        facturaPresupuestoSearchRepository.save(facturaPresupuesto);
        return result;
    }

    /**
     *  Get all the facturaPresupuestos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FacturaPresupuestoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FacturaPresupuestos");
        return facturaPresupuestoRepository.findAll(pageable)
            .map(facturaPresupuestoMapper::toDto);
    }

    /**
     *  Get one facturaPresupuesto by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FacturaPresupuestoDTO findOne(Long id) {
        log.debug("Request to get FacturaPresupuesto : {}", id);
        FacturaPresupuesto facturaPresupuesto = facturaPresupuestoRepository.findOne(id);
        return facturaPresupuestoMapper.toDto(facturaPresupuesto);
    }

    /**
     *  Delete the  facturaPresupuesto by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FacturaPresupuesto : {}", id);
        facturaPresupuestoRepository.delete(id);
        facturaPresupuestoSearchRepository.delete(id);
    }

    /**
     * Search for the facturaPresupuesto corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FacturaPresupuestoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FacturaPresupuestos for query {}", query);
        Page<FacturaPresupuesto> result = facturaPresupuestoSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(facturaPresupuestoMapper::toDto);
    }
}
