package com.anelsoftware.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anelsoftware.service.PagoService;
import com.anelsoftware.web.rest.util.HeaderUtil;
import com.anelsoftware.web.rest.util.PaginationUtil;
import com.anelsoftware.service.dto.PagoDTO;
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
 * REST controller for managing Pago.
 */
@RestController
@RequestMapping("/api")
public class PagoResource {

    private final Logger log = LoggerFactory.getLogger(PagoResource.class);

    private static final String ENTITY_NAME = "pago";

    private final PagoService pagoService;

    public PagoResource(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    /**
     * POST  /pagos : Create a new pago.
     *
     * @param pagoDTO the pagoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pagoDTO, or with status 400 (Bad Request) if the pago has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pagos")
    @Timed
    public ResponseEntity<PagoDTO> createPago(@Valid @RequestBody PagoDTO pagoDTO) throws URISyntaxException {
        log.debug("REST request to save Pago : {}", pagoDTO);
        if (pagoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pago cannot already have an ID")).body(null);
        }
        PagoDTO result = pagoService.save(pagoDTO);
        return ResponseEntity.created(new URI("/api/pagos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pagos : Updates an existing pago.
     *
     * @param pagoDTO the pagoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pagoDTO,
     * or with status 400 (Bad Request) if the pagoDTO is not valid,
     * or with status 500 (Internal Server Error) if the pagoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pagos")
    @Timed
    public ResponseEntity<PagoDTO> updatePago(@Valid @RequestBody PagoDTO pagoDTO) throws URISyntaxException {
        log.debug("REST request to update Pago : {}", pagoDTO);
        if (pagoDTO.getId() == null) {
            return createPago(pagoDTO);
        }
        PagoDTO result = pagoService.save(pagoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pagoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pagos : get all the pagos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pagos in body
     */
    @GetMapping("/pagos")
    @Timed
    public ResponseEntity<List<PagoDTO>> getAllPagos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Pagos");
        Page<PagoDTO> page = pagoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pagos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pagos : get all the pagos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pagos in body
     */
    @GetMapping("/pagos/encargo/{encargoId}")
    @Timed
    public ResponseEntity<List<PagoDTO>> getAllPagosEncargo(@ApiParam Pageable pageable,@PathVariable Long encargoId) {
        log.debug("REST request to get a page of Pagos");
        Page<PagoDTO> page = pagoService.findAllByEncargoId(pageable, encargoId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pagos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pagos/:id : get the "id" pago.
     *
     * @param id the id of the pagoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pagoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pagos/{id}")
    @Timed
    public ResponseEntity<PagoDTO> getPago(@PathVariable Long id) {
        log.debug("REST request to get Pago : {}", id);
        PagoDTO pagoDTO = pagoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pagoDTO));
    }

    /**
     * DELETE  /pagos/:id : delete the "id" pago.
     *
     * @param id the id of the pagoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pagos/{id}")
    @Timed
    public ResponseEntity<Void> deletePago(@PathVariable Long id) {
        log.debug("REST request to delete Pago : {}", id);
        pagoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/pagos?query=:query : search for the pago corresponding
     * to the query.
     *
     * @param query the query of the pago search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/pagos")
    @Timed
    public ResponseEntity<List<PagoDTO>> searchPagos(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Pagos for query {}", query);
        Page<PagoDTO> page = pagoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/pagos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
