package com.anelsoftware.service.impl;

import com.anelsoftware.domain.Cliente;
import com.anelsoftware.repository.ClienteRepository;
import com.anelsoftware.service.MedidaService;
import com.anelsoftware.domain.Medida;
import com.anelsoftware.repository.MedidaRepository;
import com.anelsoftware.service.dto.MedidaDTO;
import com.anelsoftware.service.mapper.MedidaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Medida.
 */
@Service
@Transactional
public class MedidaServiceImpl implements MedidaService{

    private final Logger log = LoggerFactory.getLogger(MedidaServiceImpl.class);

    private final MedidaRepository medidaRepository;

    private final ClienteRepository clienteRepository;

    private final MedidaMapper medidaMapper;

    public MedidaServiceImpl(MedidaRepository medidaRepository, MedidaMapper medidaMapper, ClienteRepository clienteRepository) {
        this.medidaRepository = medidaRepository;
        this.medidaMapper = medidaMapper;
        this.clienteRepository = clienteRepository;
    }

    /**
     * Save a medida.
     *
     * @param medidaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MedidaDTO save(MedidaDTO medidaDTO) {
        log.debug("Request to save Medida : {}", medidaDTO);
        Medida medida = medidaMapper.toEntity(medidaDTO);
        medida = medidaRepository.save(medida);
        MedidaDTO result = medidaMapper.toDto(medida);
        return result;
    }

    /**
     *  Get all the medidas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MedidaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Medidas");
        Page<Medida> result = medidaRepository.findAll(pageable);
        return result.map(medida -> medidaMapper.toDto(medida));
    }

    /**
     *  Get one medida by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MedidaDTO findOne(Long id) {
        log.debug("Request to get Medida : {}", id);
        Medida medida = medidaRepository.findOne(id);
        MedidaDTO medidaDTO = medidaMapper.toDto(medida);
        return medidaDTO;
    }

    /**
     *  Delete the  medida by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Medida : {}", id);
        medidaRepository.delete(id);
    }

    /**
     * Get all medidas from selected Client
     *
     * @param pageable
     * @param clienteId
     * @return
     */
    @Override
    public Page<MedidaDTO> findAllByCliente(Pageable pageable, Long clienteId) {
        log.debug("Request to get all Medidas");
        Cliente cliente = clienteRepository.findOne(clienteId);
        Page<Medida> result = medidaRepository.findAllByCliente(pageable, cliente);
        return result.map(medida -> medidaMapper.toDto(medida));
    }
}
