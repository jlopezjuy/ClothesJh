package com.anelsoftware.service.impl;

import com.anelsoftware.domain.Cliente;
import com.anelsoftware.repository.ClienteRepository;
import com.anelsoftware.service.ModeloService;
import com.anelsoftware.domain.Modelo;
import com.anelsoftware.repository.ModeloRepository;
import com.anelsoftware.service.dto.ModeloDTO;
import com.anelsoftware.service.mapper.ModeloMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Modelo.
 */
@Service
@Transactional
public class ModeloServiceImpl implements ModeloService{

    private final Logger log = LoggerFactory.getLogger(ModeloServiceImpl.class);

    private final ModeloRepository modeloRepository;
    private final ClienteRepository clienteRepository;

    private final ModeloMapper modeloMapper;

    public ModeloServiceImpl(ModeloRepository modeloRepository, ModeloMapper modeloMapper, ClienteRepository clienteRepository) {
        this.modeloRepository = modeloRepository;
        this.modeloMapper = modeloMapper;
        this.clienteRepository = clienteRepository;
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
        Page<Modelo> result = modeloRepository.findAll(pageable);
        return result.map(modelo -> modeloMapper.toDto(modelo));
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
        ModeloDTO modeloDTO = modeloMapper.toDto(modelo);
        return modeloDTO;
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
    }

    /**
     *
     * @param pageable
     * @param clienteId
     * @return
     */
    @Override
    public Page<ModeloDTO> findAllByCliente(Pageable pageable, Long clienteId) {
        log.debug("Request to get all Modelos");
        Cliente cliente  = clienteRepository.findOne(clienteId);
        Page<Modelo> result = modeloRepository.findAllByCliente(pageable, cliente);
        return result.map(modelo -> modeloMapper.toDto(modelo));
    }
}
