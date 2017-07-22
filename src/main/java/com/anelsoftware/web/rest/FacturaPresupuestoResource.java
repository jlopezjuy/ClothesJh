package com.anelsoftware.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anelsoftware.service.FacturaPresupuestoService;
import com.anelsoftware.web.rest.util.HeaderUtil;
import com.anelsoftware.web.rest.util.PaginationUtil;
import com.anelsoftware.service.dto.FacturaPresupuestoDTO;
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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing FacturaPresupuesto.
 */
@RestController
@RequestMapping("/api")
public class FacturaPresupuestoResource {

    private final Logger log = LoggerFactory.getLogger(FacturaPresupuestoResource.class);

    private static final String ENTITY_NAME = "facturaPresupuesto";

    private final FacturaPresupuestoService facturaPresupuestoService;

    public FacturaPresupuestoResource(FacturaPresupuestoService facturaPresupuestoService) {
        this.facturaPresupuestoService = facturaPresupuestoService;
    }

    /**
     * POST  /factura-presupuestos : Create a new facturaPresupuesto.
     *
     * @param facturaPresupuestoDTO the facturaPresupuestoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new facturaPresupuestoDTO, or with status 400 (Bad Request) if the facturaPresupuesto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/factura-presupuestos")
    @Timed
    public ResponseEntity<FacturaPresupuestoDTO> createFacturaPresupuesto(@Valid @RequestBody FacturaPresupuestoDTO facturaPresupuestoDTO) throws URISyntaxException {
        log.debug("REST request to save FacturaPresupuesto : {}", facturaPresupuestoDTO);
        if (facturaPresupuestoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new facturaPresupuesto cannot already have an ID")).body(null);
        }
        FacturaPresupuestoDTO result = facturaPresupuestoService.save(facturaPresupuestoDTO);
        return ResponseEntity.created(new URI("/api/factura-presupuestos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /factura-presupuestos : Updates an existing facturaPresupuesto.
     *
     * @param facturaPresupuestoDTO the facturaPresupuestoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated facturaPresupuestoDTO,
     * or with status 400 (Bad Request) if the facturaPresupuestoDTO is not valid,
     * or with status 500 (Internal Server Error) if the facturaPresupuestoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/factura-presupuestos")
    @Timed
    public ResponseEntity<FacturaPresupuestoDTO> updateFacturaPresupuesto(@Valid @RequestBody FacturaPresupuestoDTO facturaPresupuestoDTO) throws URISyntaxException {
        log.debug("REST request to update FacturaPresupuesto : {}", facturaPresupuestoDTO);
        if (facturaPresupuestoDTO.getId() == null) {
            return createFacturaPresupuesto(facturaPresupuestoDTO);
        }
        FacturaPresupuestoDTO result = facturaPresupuestoService.save(facturaPresupuestoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, facturaPresupuestoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /factura-presupuestos : get all the facturaPresupuestos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of facturaPresupuestos in body
     */
    @GetMapping("/factura-presupuestos")
    @Timed
    public ResponseEntity<List<FacturaPresupuestoDTO>> getAllFacturaPresupuestos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of FacturaPresupuestos");
        Page<FacturaPresupuestoDTO> page = facturaPresupuestoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/factura-presupuestos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /factura-presupuestos/:id : get the "id" facturaPresupuesto.
     *
     * @param id the id of the facturaPresupuestoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the facturaPresupuestoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/factura-presupuestos/{id}")
    @Timed
    public ResponseEntity<FacturaPresupuestoDTO> getFacturaPresupuesto(@PathVariable Long id) {
        log.debug("REST request to get FacturaPresupuesto : {}", id);
        FacturaPresupuestoDTO facturaPresupuestoDTO = facturaPresupuestoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(facturaPresupuestoDTO));
    }

    /**
     * DELETE  /factura-presupuestos/:id : delete the "id" facturaPresupuesto.
     *
     * @param id the id of the facturaPresupuestoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/factura-presupuestos/{id}")
    @Timed
    public ResponseEntity<Void> deleteFacturaPresupuesto(@PathVariable Long id) {
        log.debug("REST request to delete FacturaPresupuesto : {}", id);
        facturaPresupuestoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/factura-presupuestos?query=:query : search for the facturaPresupuesto corresponding
     * to the query.
     *
     * @param query the query of the facturaPresupuesto search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/factura-presupuestos")
    @Timed
    public ResponseEntity<List<FacturaPresupuestoDTO>> searchFacturaPresupuestos(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of FacturaPresupuestos for query {}", query);
        Page<FacturaPresupuestoDTO> page = facturaPresupuestoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/factura-presupuestos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
