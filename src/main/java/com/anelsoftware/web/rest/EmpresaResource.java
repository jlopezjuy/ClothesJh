package com.anelsoftware.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anelsoftware.service.EmpresaService;
import com.anelsoftware.web.rest.util.HeaderUtil;
import com.anelsoftware.web.rest.util.PaginationUtil;
import com.anelsoftware.service.dto.EmpresaDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Empresa.
 */
@RestController
@RequestMapping("/api")
public class EmpresaResource {

    private final Logger log = LoggerFactory.getLogger(EmpresaResource.class);

    private static final String ENTITY_NAME = "empresa";
        
    private final EmpresaService empresaService;

    public EmpresaResource(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    /**
     * POST  /empresas : Create a new empresa.
     *
     * @param empresaDTO the empresaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new empresaDTO, or with status 400 (Bad Request) if the empresa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/empresas")
    @Timed
    public ResponseEntity<EmpresaDTO> createEmpresa(@Valid @RequestBody EmpresaDTO empresaDTO) throws URISyntaxException {
        log.debug("REST request to save Empresa : {}", empresaDTO);
        if (empresaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new empresa cannot already have an ID")).body(null);
        }
        EmpresaDTO result = empresaService.save(empresaDTO);
        return ResponseEntity.created(new URI("/api/empresas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /empresas : Updates an existing empresa.
     *
     * @param empresaDTO the empresaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated empresaDTO,
     * or with status 400 (Bad Request) if the empresaDTO is not valid,
     * or with status 500 (Internal Server Error) if the empresaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/empresas")
    @Timed
    public ResponseEntity<EmpresaDTO> updateEmpresa(@Valid @RequestBody EmpresaDTO empresaDTO) throws URISyntaxException {
        log.debug("REST request to update Empresa : {}", empresaDTO);
        if (empresaDTO.getId() == null) {
            return createEmpresa(empresaDTO);
        }
        EmpresaDTO result = empresaService.save(empresaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, empresaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /empresas : get all the empresas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of empresas in body
     */
    @GetMapping("/empresas")
    @Timed
    public ResponseEntity<List<EmpresaDTO>> getAllEmpresas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Empresas");
        Page<EmpresaDTO> page = empresaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/empresas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /empresas/:id : get the "id" empresa.
     *
     * @param id the id of the empresaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the empresaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/empresas/{id}")
    @Timed
    public ResponseEntity<EmpresaDTO> getEmpresa(@PathVariable Long id) {
        log.debug("REST request to get Empresa : {}", id);
        EmpresaDTO empresaDTO = empresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(empresaDTO));
    }

    /**
     * DELETE  /empresas/:id : delete the "id" empresa.
     *
     * @param id the id of the empresaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/empresas/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmpresa(@PathVariable Long id) {
        log.debug("REST request to delete Empresa : {}", id);
        empresaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
