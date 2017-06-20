package com.anelsoftware.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anelsoftware.service.MedidaService;
import com.anelsoftware.web.rest.util.HeaderUtil;
import com.anelsoftware.web.rest.util.PaginationUtil;
import com.anelsoftware.service.dto.MedidaDTO;
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
 * REST controller for managing Medida.
 */
@RestController
@RequestMapping("/api")
public class MedidaResource {

    private final Logger log = LoggerFactory.getLogger(MedidaResource.class);

    private static final String ENTITY_NAME = "medida";

    private final MedidaService medidaService;

    public MedidaResource(MedidaService medidaService) {
        this.medidaService = medidaService;
    }

    /**
     * POST  /medidas : Create a new medida.
     *
     * @param medidaDTO the medidaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medidaDTO, or with status 400 (Bad Request) if the medida has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medidas")
    @Timed
    public ResponseEntity<MedidaDTO> createMedida(@Valid @RequestBody MedidaDTO medidaDTO) throws URISyntaxException {
        log.debug("REST request to save Medida : {}", medidaDTO);
        if (medidaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new medida cannot already have an ID")).body(null);
        }
        MedidaDTO result = medidaService.save(medidaDTO);
        return ResponseEntity.created(new URI("/api/medidas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medidas : Updates an existing medida.
     *
     * @param medidaDTO the medidaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medidaDTO,
     * or with status 400 (Bad Request) if the medidaDTO is not valid,
     * or with status 500 (Internal Server Error) if the medidaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medidas")
    @Timed
    public ResponseEntity<MedidaDTO> updateMedida(@Valid @RequestBody MedidaDTO medidaDTO) throws URISyntaxException {
        log.debug("REST request to update Medida : {}", medidaDTO);
        if (medidaDTO.getId() == null) {
            return createMedida(medidaDTO);
        }
        MedidaDTO result = medidaService.save(medidaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medidaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medidas : get all the medidas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of medidas in body
     */
    @GetMapping("/medidas")
    @Timed
    public ResponseEntity<List<MedidaDTO>> getAllMedidas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Medidas");
        Page<MedidaDTO> page = medidaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/medidas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /medidas : get all the medidas by Encargo.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of medidas in body
     */
    @GetMapping("/medidas/encargo/{encargoId}")
    @Timed
    public ResponseEntity<List<MedidaDTO>> getAllMedidasEncargo(@ApiParam Pageable pageable, @PathVariable Long encargoId) {
        log.debug("REST request to get a page of Medidas");
        Page<MedidaDTO> page = medidaService.findAllByEncargoId(pageable, encargoId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/medidas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /medidas/:id : get the "id" medida.
     *
     * @param id the id of the medidaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medidaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/medidas/{id}")
    @Timed
    public ResponseEntity<MedidaDTO> getMedida(@PathVariable Long id) {
        log.debug("REST request to get Medida : {}", id);
        MedidaDTO medidaDTO = medidaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(medidaDTO));
    }

    /**
     * DELETE  /medidas/:id : delete the "id" medida.
     *
     * @param id the id of the medidaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medidas/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedida(@PathVariable Long id) {
        log.debug("REST request to delete Medida : {}", id);
        medidaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
