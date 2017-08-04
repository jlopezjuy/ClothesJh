package com.anelsoftware.service.impl;

import com.anelsoftware.domain.FacturaPresupuesto;
import com.anelsoftware.repository.FacturaPresupuestoRepository;
import com.anelsoftware.service.DetalleFactPresService;
import com.anelsoftware.domain.DetalleFactPres;
import com.anelsoftware.repository.DetalleFactPresRepository;
import com.anelsoftware.repository.search.DetalleFactPresSearchRepository;
import com.anelsoftware.service.dto.DetalleFactPresDTO;
import com.anelsoftware.service.mapper.DetalleFactPresMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DetalleFactPres.
 */
@Service
@Transactional
public class DetalleFactPresServiceImpl implements DetalleFactPresService{

    private final Logger log = LoggerFactory.getLogger(DetalleFactPresServiceImpl.class);

    private final DetalleFactPresRepository detalleFactPresRepository;
    private final FacturaPresupuestoRepository facturaPresupuestoRepository;

    private final DetalleFactPresMapper detalleFactPresMapper;

    private final DetalleFactPresSearchRepository detalleFactPresSearchRepository;

    public DetalleFactPresServiceImpl(DetalleFactPresRepository detalleFactPresRepository, DetalleFactPresMapper detalleFactPresMapper,
                                      FacturaPresupuestoRepository facturaPresupuestoRepository, DetalleFactPresSearchRepository detalleFactPresSearchRepository) {
        this.detalleFactPresRepository = detalleFactPresRepository;
        this.detalleFactPresMapper = detalleFactPresMapper;
        this.facturaPresupuestoRepository = facturaPresupuestoRepository;
        this.detalleFactPresSearchRepository = detalleFactPresSearchRepository;
    }

    /**
     * Save a detalleFactPres.
     *
     * @param detalleFactPresDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DetalleFactPresDTO save(DetalleFactPresDTO detalleFactPresDTO) {
        log.debug("Request to save DetalleFactPres : {}", detalleFactPresDTO);
        DetalleFactPres detalleFactPres = detalleFactPresMapper.toEntity(detalleFactPresDTO);
        detalleFactPres = detalleFactPresRepository.save(detalleFactPres);
        DetalleFactPresDTO result = detalleFactPresMapper.toDto(detalleFactPres);
        detalleFactPresSearchRepository.save(detalleFactPres);
        return result;
    }

    /**
     *  Get all the detalleFactPres.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DetalleFactPresDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DetalleFactPres");
        return detalleFactPresRepository.findAll(pageable)
            .map(detalleFactPresMapper::toDto);
    }

    /**
     *  Get one detalleFactPres by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DetalleFactPresDTO findOne(Long id) {
        log.debug("Request to get DetalleFactPres : {}", id);
        DetalleFactPres detalleFactPres = detalleFactPresRepository.findOne(id);
        return detalleFactPresMapper.toDto(detalleFactPres);
    }

    /**
     *  Delete the  detalleFactPres by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DetalleFactPres : {}", id);
        detalleFactPresRepository.delete(id);
        detalleFactPresSearchRepository.delete(id);
    }

    @Override
    public List<DetalleFactPresDTO> findAllByFacturaPresupuestoId(Long facturaPresupuestoId) {
        log.debug("Request to get all DetalleFactPres");
        FacturaPresupuesto facturaPresupuesto = facturaPresupuestoRepository.findOne(facturaPresupuestoId);
        List<DetalleFactPres> list = detalleFactPresRepository.findAllByFacturaPresupuesto(facturaPresupuesto);
        return detalleFactPresMapper.toDto(list);
    }

    /**
     * Search for the detalleFactPres corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DetalleFactPresDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DetalleFactPres for query {}", query);
        Page<DetalleFactPres> result = detalleFactPresSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(detalleFactPresMapper::toDto);
    }
}
