package com.anelsoftware.service.impl;

import com.anelsoftware.service.FacturaPresupuestoService;
import com.anelsoftware.domain.FacturaPresupuesto;
import com.anelsoftware.repository.FacturaPresupuestoRepository;
import com.anelsoftware.service.dto.FacturaPresupuestoDTO;
import com.anelsoftware.service.mapper.FacturaPresupuestoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing FacturaPresupuesto.
 */
@Service
@Transactional
public class FacturaPresupuestoServiceImpl implements FacturaPresupuestoService{

    private final Logger log = LoggerFactory.getLogger(FacturaPresupuestoServiceImpl.class);

    private final FacturaPresupuestoRepository facturaPresupuestoRepository;

    private final FacturaPresupuestoMapper facturaPresupuestoMapper;

    public FacturaPresupuestoServiceImpl(FacturaPresupuestoRepository facturaPresupuestoRepository, FacturaPresupuestoMapper facturaPresupuestoMapper) {
        this.facturaPresupuestoRepository = facturaPresupuestoRepository;
        this.facturaPresupuestoMapper = facturaPresupuestoMapper;
    }

    /**
     * Save a facturaPresupuesto.
     *
     * @param facturaPresupuestoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FacturaPresupuestoDTO save(FacturaPresupuestoDTO facturaPresupuestoDTO) {
        log.debug("Request to save FacturaPresupuesto : {}", facturaPresupuestoDTO);
        FacturaPresupuesto facturaPresupuesto = facturaPresupuestoMapper.toEntity(facturaPresupuestoDTO);
        facturaPresupuesto = facturaPresupuestoRepository.save(facturaPresupuesto);
        return facturaPresupuestoMapper.toDto(facturaPresupuesto);
    }

    /**
     *  Get all the facturaPresupuestos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FacturaPresupuestoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FacturaPresupuestos");
        return facturaPresupuestoRepository.findAll(pageable)
            .map(facturaPresupuestoMapper::toDto);
    }

    /**
     *  Get one facturaPresupuesto by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FacturaPresupuestoDTO findOne(Long id) {
        log.debug("Request to get FacturaPresupuesto : {}", id);
        FacturaPresupuesto facturaPresupuesto = facturaPresupuestoRepository.findOne(id);
        return facturaPresupuestoMapper.toDto(facturaPresupuesto);
    }

    /**
     *  Delete the  facturaPresupuesto by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FacturaPresupuesto : {}", id);
        facturaPresupuestoRepository.delete(id);
    }
}
