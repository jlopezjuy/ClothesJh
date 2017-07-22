package com.anelsoftware.service.impl;

import com.anelsoftware.domain.Encargo;
import com.anelsoftware.repository.EncargoRepository;
import com.anelsoftware.service.PagoService;
import com.anelsoftware.domain.Pago;
import com.anelsoftware.repository.PagoRepository;
import com.anelsoftware.repository.search.PagoSearchRepository;
import com.anelsoftware.service.dto.PagoDTO;
import com.anelsoftware.service.mapper.PagoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Pago.
 */
@Service
@Transactional
public class PagoServiceImpl implements PagoService{

    private final Logger log = LoggerFactory.getLogger(PagoServiceImpl.class);

    private final PagoRepository pagoRepository;
    private final EncargoRepository encargoRepository;

    private final PagoMapper pagoMapper;

    private final PagoSearchRepository pagoSearchRepository;

    public PagoServiceImpl(PagoRepository pagoRepository, PagoMapper pagoMapper, PagoSearchRepository pagoSearchRepository,
                           EncargoRepository encargoRepository) {
        this.pagoRepository = pagoRepository;
        this.pagoMapper = pagoMapper;
        this.encargoRepository = encargoRepository;
        this.pagoSearchRepository = pagoSearchRepository;
    }

    /**
     * Save a pago.
     *
     * @param pagoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PagoDTO save(PagoDTO pagoDTO) {
        log.debug("Request to save Pago : {}", pagoDTO);
        Pago pago = pagoMapper.toEntity(pagoDTO);
        pago = pagoRepository.save(pago);
        PagoDTO result = pagoMapper.toDto(pago);
        pagoSearchRepository.save(pago);
        return result;
    }

    /**
     *  Get all the pagos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PagoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pagos");
        return pagoRepository.findAll(pageable)
            .map(pagoMapper::toDto);
    }

    /**
     *  Get one pago by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PagoDTO findOne(Long id) {
        log.debug("Request to get Pago : {}", id);
        Pago pago = pagoRepository.findOne(id);
        return pagoMapper.toDto(pago);
    }

    /**
     *  Delete the  pago by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pago : {}", id);
        pagoRepository.delete(id);
        pagoSearchRepository.delete(id);
    }

    /**
     * @param pageable
     * @param encargoId
     * @return
     */
    @Override
    public Page<PagoDTO> findAllByEncargoId(Pageable pageable, Long encargoId) {
        log.debug("Request to get all Pagos");
        Encargo encargo = encargoRepository.findOne(encargoId);
        return pagoRepository.findAllByEncargo(pageable, encargo)
            .map(pagoMapper::toDto);
    }

    /**
     * Search for the pago corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PagoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Pagos for query {}", query);
        Page<Pago> result = pagoSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(pagoMapper::toDto);
    }
}
