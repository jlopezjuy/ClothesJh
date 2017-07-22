package com.anelsoftware.service.impl;

import com.anelsoftware.domain.Encargo;
import com.anelsoftware.repository.EncargoRepository;
import com.anelsoftware.service.ModeloService;
import com.anelsoftware.domain.Modelo;
import com.anelsoftware.repository.ModeloRepository;
import com.anelsoftware.repository.search.ModeloSearchRepository;
import com.anelsoftware.service.dto.ModeloDTO;
import com.anelsoftware.service.mapper.ModeloMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Modelo.
 */
@Service
@Transactional
public class ModeloServiceImpl implements ModeloService{

    private final Logger log = LoggerFactory.getLogger(ModeloServiceImpl.class);

    private final ModeloRepository modeloRepository;
    private final EncargoRepository encargoRepository;

    private final ModeloMapper modeloMapper;

    private final ModeloSearchRepository modeloSearchRepository;

    public ModeloServiceImpl(ModeloRepository modeloRepository, ModeloMapper modeloMapper, ModeloSearchRepository modeloSearchRepository,
                             EncargoRepository encargoRepository) {
        this.modeloRepository = modeloRepository;
        this.modeloMapper = modeloMapper;
        this.encargoRepository = encargoRepository;
        this.modeloSearchRepository = modeloSearchRepository;
    }

    /**
     * Save a modelo.
     *
     * @param modeloDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ModeloDTO save(ModeloDTO modeloDTO) {
        log.debug("Request to save Modelo : {}", modeloDTO);
        Modelo modelo = modeloMapper.toEntity(modeloDTO);
        modelo = modeloRepository.save(modelo);
        ModeloDTO result = modeloMapper.toDto(modelo);
        modeloSearchRepository.save(modelo);
        return result;
    }

    /**
     *  Get all the modelos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ModeloDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Modelos");
        return modeloRepository.findAll(pageable)
            .map(modeloMapper::toDto);
    }

    /**
     *  Get one modelo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ModeloDTO findOne(Long id) {
        log.debug("Request to get Modelo : {}", id);
        Modelo modelo = modeloRepository.findOne(id);
        return modeloMapper.toDto(modelo);
    }

    /**
     *  Delete the  modelo by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Modelo : {}", id);
        modeloRepository.delete(id);
        modeloSearchRepository.delete(id);
    }

    /**
     * @param pageable
     * @param encargoId
     * @return
     */
    @Override
    public Page<ModeloDTO> findAllByEncargoId(Pageable pageable, Long encargoId) {
        log.debug("Request to get all Modelos");
        Encargo encargo = encargoRepository.findOne(encargoId);
        return modeloRepository.findAllByEncargo(pageable, encargo)
            .map(modeloMapper::toDto);
    }

    /**
     * Search for the modelo corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ModeloDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Modelos for query {}", query);
        Page<Modelo> result = modeloSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(modeloMapper::toDto);
    }
}
