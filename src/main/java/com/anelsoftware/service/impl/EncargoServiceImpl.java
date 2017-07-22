package com.anelsoftware.service.impl;

import com.anelsoftware.service.EncargoService;
import com.anelsoftware.domain.Encargo;
import com.anelsoftware.repository.EncargoRepository;
import com.anelsoftware.repository.search.EncargoSearchRepository;
import com.anelsoftware.service.dto.EncargoDTO;
import com.anelsoftware.service.mapper.EncargoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Encargo.
 */
@Service
@Transactional
public class EncargoServiceImpl implements EncargoService{

    private final Logger log = LoggerFactory.getLogger(EncargoServiceImpl.class);

    private final EncargoRepository encargoRepository;

    private final EncargoMapper encargoMapper;

    private final EncargoSearchRepository encargoSearchRepository;

    public EncargoServiceImpl(EncargoRepository encargoRepository, EncargoMapper encargoMapper, EncargoSearchRepository encargoSearchRepository) {
        this.encargoRepository = encargoRepository;
        this.encargoMapper = encargoMapper;
        this.encargoSearchRepository = encargoSearchRepository;
    }

    /**
     * Save a encargo.
     *
     * @param encargoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EncargoDTO save(EncargoDTO encargoDTO) {
        log.debug("Request to save Encargo : {}", encargoDTO);
        Encargo encargo = encargoMapper.toEntity(encargoDTO);
        encargo = encargoRepository.save(encargo);
        EncargoDTO result = encargoMapper.toDto(encargo);
        encargoSearchRepository.save(encargo);
        return result;
    }

    /**
     *  Get all the encargos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EncargoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Encargos");
        return encargoRepository.findAll(pageable)
            .map(encargoMapper::toDto);
    }

    /**
     *  Get one encargo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EncargoDTO findOne(Long id) {
        log.debug("Request to get Encargo : {}", id);
        Encargo encargo = encargoRepository.findOne(id);
        return encargoMapper.toDto(encargo);
    }

    /**
     *  Delete the  encargo by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Encargo : {}", id);
        encargoRepository.delete(id);
        encargoSearchRepository.delete(id);
    }

    /**
     * Search for the encargo corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EncargoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Encargos for query {}", query);
        Page<Encargo> result = encargoSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(encargoMapper::toDto);
    }
}
