package com.anelsoftware.service.impl;

import com.anelsoftware.domain.Encargo;
import com.anelsoftware.repository.EncargoRepository;
import com.anelsoftware.service.ModeloEncargoService;
import com.anelsoftware.domain.ModeloEncargo;
import com.anelsoftware.repository.ModeloEncargoRepository;
import com.anelsoftware.service.dto.ModeloEncargoDTO;
import com.anelsoftware.service.mapper.ModeloEncargoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ModeloEncargo.
 */
@Service
@Transactional
public class ModeloEncargoServiceImpl implements ModeloEncargoService{

    private final Logger log = LoggerFactory.getLogger(ModeloEncargoServiceImpl.class);

    private final ModeloEncargoRepository modeloEncargoRepository;
    private final EncargoRepository encargoRepository;

    private final ModeloEncargoMapper modeloEncargoMapper;

    public ModeloEncargoServiceImpl(ModeloEncargoRepository modeloEncargoRepository, ModeloEncargoMapper modeloEncargoMapper, EncargoRepository encargoRepository) {
        this.modeloEncargoRepository = modeloEncargoRepository;
        this.modeloEncargoMapper = modeloEncargoMapper;
        this.encargoRepository = encargoRepository;
    }

    /**
     * Save a modeloEncargo.
     *
     * @param modeloEncargoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ModeloEncargoDTO save(ModeloEncargoDTO modeloEncargoDTO) {
        log.debug("Request to save ModeloEncargo : {}", modeloEncargoDTO);
        ModeloEncargo modeloEncargo = modeloEncargoMapper.toEntity(modeloEncargoDTO);
        modeloEncargo = modeloEncargoRepository.save(modeloEncargo);
        return modeloEncargoMapper.toDto(modeloEncargo);
    }

    /**
     *  Get all the modeloEncargos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ModeloEncargoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ModeloEncargos");
        return modeloEncargoRepository.findAll(pageable)
            .map(modeloEncargoMapper::toDto);
    }

    /**
     *  Get one modeloEncargo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ModeloEncargoDTO findOne(Long id) {
        log.debug("Request to get ModeloEncargo : {}", id);
        ModeloEncargo modeloEncargo = modeloEncargoRepository.findOne(id);
        return modeloEncargoMapper.toDto(modeloEncargo);
    }

    /**
     *  Delete the  modeloEncargo by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ModeloEncargo : {}", id);
        modeloEncargoRepository.delete(id);
    }

    /**
     * @param pageable
     * @param encargoId
     * @return
     */
    @Override
    public Page<ModeloEncargoDTO> findAllByEncargoId(Pageable pageable, Long encargoId) {
        log.debug("Request to get all ModeloEncargos");
        Encargo encargo = encargoRepository.findOne(encargoId);
        return modeloEncargoRepository.findAllByEncargo(pageable, encargo)
            .map(modeloEncargoMapper::toDto);
    }
}
