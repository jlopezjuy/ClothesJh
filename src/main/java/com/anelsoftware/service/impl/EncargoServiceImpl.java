package com.anelsoftware.service.impl;

import com.anelsoftware.service.EncargoService;
import com.anelsoftware.domain.Encargo;
import com.anelsoftware.repository.EncargoRepository;
import com.anelsoftware.service.dto.EncargoDTO;
import com.anelsoftware.service.mapper.EncargoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Encargo.
 */
@Service
@Transactional
public class EncargoServiceImpl implements EncargoService{

    private final Logger log = LoggerFactory.getLogger(EncargoServiceImpl.class);

    private final EncargoRepository encargoRepository;

    private final EncargoMapper encargoMapper;

    public EncargoServiceImpl(EncargoRepository encargoRepository, EncargoMapper encargoMapper) {
        this.encargoRepository = encargoRepository;
        this.encargoMapper = encargoMapper;
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
        return encargoMapper.toDto(encargo);
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
    }
}
