package com.anelsoftware.service.impl;

import com.anelsoftware.domain.Encargo;
import com.anelsoftware.repository.EncargoRepository;
import com.anelsoftware.service.MedidaService;
import com.anelsoftware.domain.Medida;
import com.anelsoftware.repository.MedidaRepository;
import com.anelsoftware.service.dto.MedidaDTO;
import com.anelsoftware.service.mapper.MedidaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Medida.
 */
@Service
@Transactional
public class MedidaServiceImpl implements MedidaService{

    private final Logger log = LoggerFactory.getLogger(MedidaServiceImpl.class);

    private final MedidaRepository medidaRepository;

    private final EncargoRepository encargoRepository;

    private final MedidaMapper medidaMapper;

    public MedidaServiceImpl(MedidaRepository medidaRepository, MedidaMapper medidaMapper, EncargoRepository encargoRepository) {
        this.medidaRepository = medidaRepository;
        this.medidaMapper = medidaMapper;
        this.encargoRepository = encargoRepository;
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
        return medidaMapper.toDto(medida);
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
        return medidaRepository.findAll(pageable)
            .map(medidaMapper::toDto);
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
        return medidaMapper.toDto(medida);
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
     * @param pageable
     * @param encargoId
     * @return
     */
    @Override
    public Page<MedidaDTO> findAllByEncargoId(Pageable pageable, Long encargoId) {
        log.debug("Request to get all Medidas");
        Encargo encargo = encargoRepository.findOne(encargoId);
        return medidaRepository.findAllByEncargo(pageable, encargo)
            .map(medidaMapper::toDto);
    }
}
