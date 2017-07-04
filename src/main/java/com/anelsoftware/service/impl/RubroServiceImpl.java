package com.anelsoftware.service.impl;

import com.anelsoftware.service.RubroService;
import com.anelsoftware.domain.Rubro;
import com.anelsoftware.repository.RubroRepository;
import com.anelsoftware.service.dto.RubroDTO;
import com.anelsoftware.service.mapper.RubroMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Rubro.
 */
@Service
@Transactional
public class RubroServiceImpl implements RubroService{

    private final Logger log = LoggerFactory.getLogger(RubroServiceImpl.class);

    private final RubroRepository rubroRepository;

    private final RubroMapper rubroMapper;

    public RubroServiceImpl(RubroRepository rubroRepository, RubroMapper rubroMapper) {
        this.rubroRepository = rubroRepository;
        this.rubroMapper = rubroMapper;
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
        return rubroMapper.toDto(rubro);
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
        return rubroRepository.findAllOrderByNombre(pageable).map(rubroMapper::toDto);
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
    }
}
