package com.anelsoftware.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anelsoftware.service.DetalleFactPresService;
import com.anelsoftware.web.rest.util.HeaderUtil;
import com.anelsoftware.web.rest.util.PaginationUtil;
import com.anelsoftware.service.dto.DetalleFactPresDTO;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DetalleFactPres.
 */
@RestController
@RequestMapping("/api")
public class DetalleFactPresResource {

    private final Logger log = LoggerFactory.getLogger(DetalleFactPresResource.class);

    private static final String ENTITY_NAME = "detalleFactPres";

    private final DetalleFactPresService detalleFactPresService;

    public DetalleFactPresResource(DetalleFactPresService detalleFactPresService) {
        this.detalleFactPresService = detalleFactPresService;
    }

    /**
     * POST  /detalle-fact-pres : Create a new detalleFactPres.
     *
     * @param detalleFactPresDTO the detalleFactPresDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new detalleFactPresDTO, or with status 400 (Bad Request) if the detalleFactPres has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/detalle-fact-pres")
    @Timed
    public ResponseEntity<DetalleFactPresDTO> createDetalleFactPres(@RequestBody DetalleFactPresDTO detalleFactPresDTO) throws URISyntaxException {
        log.debug("REST request to save DetalleFactPres : {}", detalleFactPresDTO);
        if (detalleFactPresDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new detalleFactPres cannot already have an ID")).body(null);
        }
        DetalleFactPresDTO result = detalleFactPresService.save(detalleFactPresDTO);
        return ResponseEntity.created(new URI("/api/detalle-fact-pres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /detalle-fact-pres : Updates an existing detalleFactPres.
     *
     * @param detalleFactPresDTO the detalleFactPresDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated detalleFactPresDTO,
     * or with status 400 (Bad Request) if the detalleFactPresDTO is not valid,
     * or with status 500 (Internal Server Error) if the detalleFactPresDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/detalle-fact-pres")
    @Timed
    public ResponseEntity<DetalleFactPresDTO> updateDetalleFactPres(@RequestBody DetalleFactPresDTO detalleFactPresDTO) throws URISyntaxException {
        log.debug("REST request to update DetalleFactPres : {}", detalleFactPresDTO);
        if (detalleFactPresDTO.getId() == null) {
            return createDetalleFactPres(detalleFactPresDTO);
        }
        DetalleFactPresDTO result = detalleFactPresService.save(detalleFactPresDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, detalleFactPresDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /detalle-fact-pres : get all the detalleFactPres.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of detalleFactPres in body
     */
    @GetMapping("/detalle-fact-pres")
    @Timed
    public ResponseEntity<List<DetalleFactPresDTO>> getAllDetalleFactPres(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of DetalleFactPres");
        Page<DetalleFactPresDTO> page = detalleFactPresService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/detalle-fact-pres");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /detalle-fact-pres/:id : get the "id" detalleFactPres.
     *
     * @param id the id of the detalleFactPresDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the detalleFactPresDTO, or with status 404 (Not Found)
     */
    @GetMapping("/detalle-fact-pres/{id}")
    @Timed
    public ResponseEntity<DetalleFactPresDTO> getDetalleFactPres(@PathVariable Long id) {
        log.debug("REST request to get DetalleFactPres : {}", id);
        DetalleFactPresDTO detalleFactPresDTO = detalleFactPresService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(detalleFactPresDTO));
    }

    /**
     * DELETE  /detalle-fact-pres/:id : delete the "id" detalleFactPres.
     *
     * @param id the id of the detalleFactPresDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/detalle-fact-pres/{id}")
    @Timed
    public ResponseEntity<Void> deleteDetalleFactPres(@PathVariable Long id) {
        log.debug("REST request to delete DetalleFactPres : {}", id);
        detalleFactPresService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
