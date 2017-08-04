package com.anelsoftware.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anelsoftware.service.EncargoService;
import com.anelsoftware.web.rest.util.HeaderUtil;
import com.anelsoftware.web.rest.util.PaginationUtil;
import com.anelsoftware.service.dto.EncargoDTO;
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
 * REST controller for managing Encargo.
 */
@RestController
@RequestMapping("/api")
public class EncargoResource {

    private final Logger log = LoggerFactory.getLogger(EncargoResource.class);

    private static final String ENTITY_NAME = "encargo";

    private final EncargoService encargoService;

    public EncargoResource(EncargoService encargoService) {
        this.encargoService = encargoService;
    }

    /**
     * POST  /encargos : Create a new encargo.
     *
     * @param encargoDTO the encargoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new encargoDTO, or with status 400 (Bad Request) if the encargo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/encargos")
    @Timed
    public ResponseEntity<EncargoDTO> createEncargo(@Valid @RequestBody EncargoDTO encargoDTO) throws URISyntaxException {
        log.debug("REST request to save Encargo : {}", encargoDTO);
        if (encargoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new encargo cannot already have an ID")).body(null);
        }
        EncargoDTO result = encargoService.save(encargoDTO);
        return ResponseEntity.created(new URI("/api/encargos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /encargos : Updates an existing encargo.
     *
     * @param encargoDTO the encargoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated encargoDTO,
     * or with status 400 (Bad Request) if the encargoDTO is not valid,
     * or with status 500 (Internal Server Error) if the encargoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/encargos")
    @Timed
    public ResponseEntity<EncargoDTO> updateEncargo(@Valid @RequestBody EncargoDTO encargoDTO) throws URISyntaxException {
        log.debug("REST request to update Encargo : {}", encargoDTO);
        if (encargoDTO.getId() == null) {
            return createEncargo(encargoDTO);
        }
        EncargoDTO result = encargoService.save(encargoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, encargoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /encargos : get all the encargos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of encargos in body
     */
    @GetMapping("/encargos")
    @Timed
    public ResponseEntity<List<EncargoDTO>> getAllEncargos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Encargos");
        Page<EncargoDTO> page = encargoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/encargos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /encargos/:id : get the "id" encargo.
     *
     * @param id the id of the encargoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the encargoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/encargos/{id}")
    @Timed
    public ResponseEntity<EncargoDTO> getEncargo(@PathVariable Long id) {
        log.debug("REST request to get Encargo : {}", id);
        EncargoDTO encargoDTO = encargoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(encargoDTO));
    }

    /**
     * DELETE  /encargos/:id : delete the "id" encargo.
     *
     * @param id the id of the encargoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/encargos/{id}")
    @Timed
    public ResponseEntity<Void> deleteEncargo(@PathVariable Long id) {
        log.debug("REST request to delete Encargo : {}", id);
        encargoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/encargos?query=:query : search for the encargo corresponding
     * to the query.
     *
     * @param query the query of the encargo search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/encargos")
    @Timed
    public ResponseEntity<List<EncargoDTO>> searchEncargos(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Encargos for query {}", query);
        Page<EncargoDTO> page = encargoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/encargos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
