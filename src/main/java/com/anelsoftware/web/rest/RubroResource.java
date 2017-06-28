package com.anelsoftware.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anelsoftware.service.RubroService;
import com.anelsoftware.web.rest.util.HeaderUtil;
import com.anelsoftware.web.rest.util.PaginationUtil;
import com.anelsoftware.service.dto.RubroDTO;
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
 * REST controller for managing Rubro.
 */
@RestController
@RequestMapping("/api")
public class RubroResource {

    private final Logger log = LoggerFactory.getLogger(RubroResource.class);

    private static final String ENTITY_NAME = "rubro";

    private final RubroService rubroService;

    public RubroResource(RubroService rubroService) {
        this.rubroService = rubroService;
    }

    /**
     * POST  /rubros : Create a new rubro.
     *
     * @param rubroDTO the rubroDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rubroDTO, or with status 400 (Bad Request) if the rubro has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rubros")
    @Timed
    public ResponseEntity<RubroDTO> createRubro(@RequestBody RubroDTO rubroDTO) throws URISyntaxException {
        log.debug("REST request to save Rubro : {}", rubroDTO);
        if (rubroDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new rubro cannot already have an ID")).body(null);
        }
        RubroDTO result = rubroService.save(rubroDTO);
        return ResponseEntity.created(new URI("/api/rubros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rubros : Updates an existing rubro.
     *
     * @param rubroDTO the rubroDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rubroDTO,
     * or with status 400 (Bad Request) if the rubroDTO is not valid,
     * or with status 500 (Internal Server Error) if the rubroDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rubros")
    @Timed
    public ResponseEntity<RubroDTO> updateRubro(@RequestBody RubroDTO rubroDTO) throws URISyntaxException {
        log.debug("REST request to update Rubro : {}", rubroDTO);
        if (rubroDTO.getId() == null) {
            return createRubro(rubroDTO);
        }
        RubroDTO result = rubroService.save(rubroDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rubroDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rubros : get all the rubros.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rubros in body
     */
    @GetMapping("/rubros")
    @Timed
    public ResponseEntity<List<RubroDTO>> getAllRubros(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Rubros");
        Page<RubroDTO> page = rubroService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rubros");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rubros/:id : get the "id" rubro.
     *
     * @param id the id of the rubroDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rubroDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rubros/{id}")
    @Timed
    public ResponseEntity<RubroDTO> getRubro(@PathVariable Long id) {
        log.debug("REST request to get Rubro : {}", id);
        RubroDTO rubroDTO = rubroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rubroDTO));
    }

    /**
     * DELETE  /rubros/:id : delete the "id" rubro.
     *
     * @param id the id of the rubroDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rubros/{id}")
    @Timed
    public ResponseEntity<Void> deleteRubro(@PathVariable Long id) {
        log.debug("REST request to delete Rubro : {}", id);
        rubroService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
