package com.anelsoftware.service.impl;

import com.anelsoftware.service.EmpresaService;
import com.anelsoftware.domain.Empresa;
import com.anelsoftware.repository.EmpresaRepository;
import com.anelsoftware.service.dto.EmpresaDTO;
import com.anelsoftware.service.mapper.EmpresaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Empresa.
 */
@Service
@Transactional
public class EmpresaServiceImpl implements EmpresaService{

    private final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);
    
    private final EmpresaRepository empresaRepository;

    private final EmpresaMapper empresaMapper;

    public EmpresaServiceImpl(EmpresaRepository empresaRepository, EmpresaMapper empresaMapper) {
        this.empresaRepository = empresaRepository;
        this.empresaMapper = empresaMapper;
    }

    /**
     * Save a empresa.
     *
     * @param empresaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EmpresaDTO save(EmpresaDTO empresaDTO) {
        log.debug("Request to save Empresa : {}", empresaDTO);
        Empresa empresa = empresaMapper.toEntity(empresaDTO);
        empresa = empresaRepository.save(empresa);
        EmpresaDTO result = empresaMapper.toDto(empresa);
        return result;
    }

    /**
     *  Get all the empresas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EmpresaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Empresas");
        Page<Empresa> result = empresaRepository.findAll(pageable);
        return result.map(empresa -> empresaMapper.toDto(empresa));
    }

    /**
     *  Get one empresa by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EmpresaDTO findOne(Long id) {
        log.debug("Request to get Empresa : {}", id);
        Empresa empresa = empresaRepository.findOne(id);
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);
        return empresaDTO;
    }

    /**
     *  Delete the  empresa by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Empresa : {}", id);
        empresaRepository.delete(id);
    }
}
