package com.anelsoftware.service.impl;

import com.anelsoftware.service.RubroService;
import com.anelsoftware.domain.Rubro;
import com.anelsoftware.repository.RubroRepository;
import com.anelsoftware.repository.search.RubroSearchRepository;
import com.anelsoftware.service.dto.RubroDTO;
import com.anelsoftware.service.mapper.RubroMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Rubro.
 */
@Service
@Transactional
public class RubroServiceImpl implements RubroService{

    private final Logger log = LoggerFactory.getLogger(RubroServiceImpl.class);

    private final RubroRepository rubroRepository;

    private final RubroMapper rubroMapper;

    private final RubroSearchRepository rubroSearchRepository;

    public RubroServiceImpl(RubroRepository rubroRepository, RubroMapper rubroMapper, RubroSearchRepository rubroSearchRepository) {
        this.rubroRepository = rubroRepository;
        this.rubroMapper = rubroMapper;
        this.rubroSearchRepository = rubroSearchRepository;
    }

    /**
     * Save a rubro.
     *
     * @param rubroDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RubroDTO save(RubroDTO rubroDTO) {
        log.debug("Request to save Rubro : {}", rubroDTO);
        Rubro rubro = rubroMapper.toEntity(rubroDTO);
        rubro = rubroRepository.save(rubro);
        RubroDTO result = rubroMapper.toDto(rubro);
        rubroSearchRepository.save(rubro);
        return result;
    }

    /**
     *  Get all the rubros.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RubroDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Rubros");
        return rubroRepository.findAll(pageable)
            .map(rubroMapper::toDto);
    }

    /**
     *  Get one rubro by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RubroDTO findOne(Long id) {
        log.debug("Request to get Rubro : {}", id);
        Rubro rubro = rubroRepository.findOne(id);
        return rubroMapper.toDto(rubro);
    }

    /**
     *  Delete the  rubro by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rubro : {}", id);
        rubroRepository.delete(id);
        rubroSearchRepository.delete(id);
    }

    /**
     * Search for the rubro corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RubroDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Rubros for query {}", query);
        Page<Rubro> result = rubroSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(rubroMapper::toDto);
    }
}
