package com.anelsoftware.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anelsoftware.domain.ModeloEncargo;

import com.anelsoftware.repository.ModeloEncargoRepository;
import com.anelsoftware.web.rest.util.HeaderUtil;
import com.anelsoftware.web.rest.util.PaginationUtil;
import com.anelsoftware.service.dto.ModeloEncargoDTO;
import com.anelsoftware.service.mapper.ModeloEncargoMapper;
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
 * REST controller for managing ModeloEncargo.
 */
@RestController
@RequestMapping("/api")
public class ModeloEncargoResource {

    private final Logger log = LoggerFactory.getLogger(ModeloEncargoResource.class);

    private static final String ENTITY_NAME = "modeloEncargo";

    private final ModeloEncargoRepository modeloEncargoRepository;

    private final ModeloEncargoMapper modeloEncargoMapper;

    public ModeloEncargoResource(ModeloEncargoRepository modeloEncargoRepository, ModeloEncargoMapper modeloEncargoMapper) {
        this.modeloEncargoRepository = modeloEncargoRepository;
        this.modeloEncargoMapper = modeloEncargoMapper;
    }

    /**
     * POST  /modelo-encargos : Create a new modeloEncargo.
     *
     * @param modeloEncargoDTO the modeloEncargoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new modeloEncargoDTO, or with status 400 (Bad Request) if the modeloEncargo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/modelo-encargos")
    @Timed
    public ResponseEntity<ModeloEncargoDTO> createModeloEncargo(@Valid @RequestBody ModeloEncargoDTO modeloEncargoDTO) throws URISyntaxException {
        log.debug("REST request to save ModeloEncargo : {}", modeloEncargoDTO);
        if (modeloEncargoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new modeloEncargo cannot already have an ID")).body(null);
        }
        ModeloEncargo modeloEncargo = modeloEncargoMapper.toEntity(modeloEncargoDTO);
        modeloEncargo = modeloEncargoRepository.save(modeloEncargo);
        ModeloEncargoDTO result = modeloEncargoMapper.toDto(modeloEncargo);
        return ResponseEntity.created(new URI("/api/modelo-encargos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /modelo-encargos : Updates an existing modeloEncargo.
     *
     * @param modeloEncargoDTO the modeloEncargoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated modeloEncargoDTO,
     * or with status 400 (Bad Request) if the modeloEncargoDTO is not valid,
     * or with status 500 (Internal Server Error) if the modeloEncargoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/modelo-encargos")
    @Timed
    public ResponseEntity<ModeloEncargoDTO> updateModeloEncargo(@Valid @RequestBody ModeloEncargoDTO modeloEncargoDTO) throws URISyntaxException {
        log.debug("REST request to update ModeloEncargo : {}", modeloEncargoDTO);
        if (modeloEncargoDTO.getId() == null) {
            return createModeloEncargo(modeloEncargoDTO);
        }
        ModeloEncargo modeloEncargo = modeloEncargoMapper.toEntity(modeloEncargoDTO);
        modeloEncargo = modeloEncargoRepository.save(modeloEncargo);
        ModeloEncargoDTO result = modeloEncargoMapper.toDto(modeloEncargo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, modeloEncargoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /modelo-encargos : get all the modeloEncargos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of modeloEncargos in body
     */
    @GetMapping("/modelo-encargos")
    @Timed
    public ResponseEntity<List<ModeloEncargoDTO>> getAllModeloEncargos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ModeloEncargos");
        Page<ModeloEncargo> page = modeloEncargoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/modelo-encargos");
        return new ResponseEntity<>(modeloEncargoMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /modelo-encargos/:id : get the "id" modeloEncargo.
     *
     * @param id the id of the modeloEncargoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the modeloEncargoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/modelo-encargos/{id}")
    @Timed
    public ResponseEntity<ModeloEncargoDTO> getModeloEncargo(@PathVariable Long id) {
        log.debug("REST request to get ModeloEncargo : {}", id);
        ModeloEncargo modeloEncargo = modeloEncargoRepository.findOne(id);
        ModeloEncargoDTO modeloEncargoDTO = modeloEncargoMapper.toDto(modeloEncargo);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(modeloEncargoDTO));
    }

    /**
     * DELETE  /modelo-encargos/:id : delete the "id" modeloEncargo.
     *
     * @param id the id of the modeloEncargoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/modelo-encargos/{id}")
    @Timed
    public ResponseEntity<Void> deleteModeloEncargo(@PathVariable Long id) {
        log.debug("REST request to delete ModeloEncargo : {}", id);
        modeloEncargoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
