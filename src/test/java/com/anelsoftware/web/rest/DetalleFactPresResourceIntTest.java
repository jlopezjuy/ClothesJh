package com.anelsoftware.web.rest;

import com.anelsoftware.ClothesApp;

import com.anelsoftware.domain.DetalleFactPres;
import com.anelsoftware.repository.DetalleFactPresRepository;
import com.anelsoftware.service.DetalleFactPresService;
import com.anelsoftware.repository.search.DetalleFactPresSearchRepository;
import com.anelsoftware.service.dto.DetalleFactPresDTO;
import com.anelsoftware.service.mapper.DetalleFactPresMapper;
import com.anelsoftware.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DetalleFactPresResource REST controller.
 *
 * @see DetalleFactPresResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClothesApp.class)
public class DetalleFactPresResourceIntTest {

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    private static final BigDecimal DEFAULT_PREDIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PREDIO = new BigDecimal(2);

    @Autowired
    private DetalleFactPresRepository detalleFactPresRepository;

    @Autowired
    private DetalleFactPresMapper detalleFactPresMapper;

    @Autowired
    private DetalleFactPresService detalleFactPresService;

    @Autowired
    private DetalleFactPresSearchRepository detalleFactPresSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDetalleFactPresMockMvc;

    private DetalleFactPres detalleFactPres;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DetalleFactPresResource detalleFactPresResource = new DetalleFactPresResource(detalleFactPresService);
        this.restDetalleFactPresMockMvc = MockMvcBuilders.standaloneSetup(detalleFactPresResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetalleFactPres createEntity(EntityManager em) {
        DetalleFactPres detalleFactPres = new DetalleFactPres()
            .cantidad(DEFAULT_CANTIDAD)
            .predio(DEFAULT_PREDIO);
        return detalleFactPres;
    }

    @Before
    public void initTest() {
        detalleFactPresSearchRepository.deleteAll();
        detalleFactPres = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetalleFactPres() throws Exception {
        int databaseSizeBeforeCreate = detalleFactPresRepository.findAll().size();

        // Create the DetalleFactPres
        DetalleFactPresDTO detalleFactPresDTO = detalleFactPresMapper.toDto(detalleFactPres);
        restDetalleFactPresMockMvc.perform(post("/api/detalle-fact-pres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleFactPresDTO)))
            .andExpect(status().isCreated());

        // Validate the DetalleFactPres in the database
        List<DetalleFactPres> detalleFactPresList = detalleFactPresRepository.findAll();
        assertThat(detalleFactPresList).hasSize(databaseSizeBeforeCreate + 1);
        DetalleFactPres testDetalleFactPres = detalleFactPresList.get(detalleFactPresList.size() - 1);
        assertThat(testDetalleFactPres.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testDetalleFactPres.getPredio()).isEqualTo(DEFAULT_PREDIO);

        // Validate the DetalleFactPres in Elasticsearch
        DetalleFactPres detalleFactPresEs = detalleFactPresSearchRepository.findOne(testDetalleFactPres.getId());
        assertThat(detalleFactPresEs).isEqualToComparingFieldByField(testDetalleFactPres);
    }

    @Test
    @Transactional
    public void createDetalleFactPresWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detalleFactPresRepository.findAll().size();

        // Create the DetalleFactPres with an existing ID
        detalleFactPres.setId(1L);
        DetalleFactPresDTO detalleFactPresDTO = detalleFactPresMapper.toDto(detalleFactPres);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetalleFactPresMockMvc.perform(post("/api/detalle-fact-pres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleFactPresDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DetalleFactPres> detalleFactPresList = detalleFactPresRepository.findAll();
        assertThat(detalleFactPresList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDetalleFactPres() throws Exception {
        // Initialize the database
        detalleFactPresRepository.saveAndFlush(detalleFactPres);

        // Get all the detalleFactPresList
        restDetalleFactPresMockMvc.perform(get("/api/detalle-fact-pres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalleFactPres.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].predio").value(hasItem(DEFAULT_PREDIO.intValue())));
    }

    @Test
    @Transactional
    public void getDetalleFactPres() throws Exception {
        // Initialize the database
        detalleFactPresRepository.saveAndFlush(detalleFactPres);

        // Get the detalleFactPres
        restDetalleFactPresMockMvc.perform(get("/api/detalle-fact-pres/{id}", detalleFactPres.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(detalleFactPres.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.predio").value(DEFAULT_PREDIO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDetalleFactPres() throws Exception {
        // Get the detalleFactPres
        restDetalleFactPresMockMvc.perform(get("/api/detalle-fact-pres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetalleFactPres() throws Exception {
        // Initialize the database
        detalleFactPresRepository.saveAndFlush(detalleFactPres);
        detalleFactPresSearchRepository.save(detalleFactPres);
        int databaseSizeBeforeUpdate = detalleFactPresRepository.findAll().size();

        // Update the detalleFactPres
        DetalleFactPres updatedDetalleFactPres = detalleFactPresRepository.findOne(detalleFactPres.getId());
        updatedDetalleFactPres
            .cantidad(UPDATED_CANTIDAD)
            .predio(UPDATED_PREDIO);
        DetalleFactPresDTO detalleFactPresDTO = detalleFactPresMapper.toDto(updatedDetalleFactPres);

        restDetalleFactPresMockMvc.perform(put("/api/detalle-fact-pres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleFactPresDTO)))
            .andExpect(status().isOk());

        // Validate the DetalleFactPres in the database
        List<DetalleFactPres> detalleFactPresList = detalleFactPresRepository.findAll();
        assertThat(detalleFactPresList).hasSize(databaseSizeBeforeUpdate);
        DetalleFactPres testDetalleFactPres = detalleFactPresList.get(detalleFactPresList.size() - 1);
        assertThat(testDetalleFactPres.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testDetalleFactPres.getPredio()).isEqualTo(UPDATED_PREDIO);

        // Validate the DetalleFactPres in Elasticsearch
        DetalleFactPres detalleFactPresEs = detalleFactPresSearchRepository.findOne(testDetalleFactPres.getId());
        assertThat(detalleFactPresEs).isEqualToComparingFieldByField(testDetalleFactPres);
    }

    @Test
    @Transactional
    public void updateNonExistingDetalleFactPres() throws Exception {
        int databaseSizeBeforeUpdate = detalleFactPresRepository.findAll().size();

        // Create the DetalleFactPres
        DetalleFactPresDTO detalleFactPresDTO = detalleFactPresMapper.toDto(detalleFactPres);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDetalleFactPresMockMvc.perform(put("/api/detalle-fact-pres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleFactPresDTO)))
            .andExpect(status().isCreated());

        // Validate the DetalleFactPres in the database
        List<DetalleFactPres> detalleFactPresList = detalleFactPresRepository.findAll();
        assertThat(detalleFactPresList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDetalleFactPres() throws Exception {
        // Initialize the database
        detalleFactPresRepository.saveAndFlush(detalleFactPres);
        detalleFactPresSearchRepository.save(detalleFactPres);
        int databaseSizeBeforeDelete = detalleFactPresRepository.findAll().size();

        // Get the detalleFactPres
        restDetalleFactPresMockMvc.perform(delete("/api/detalle-fact-pres/{id}", detalleFactPres.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean detalleFactPresExistsInEs = detalleFactPresSearchRepository.exists(detalleFactPres.getId());
        assertThat(detalleFactPresExistsInEs).isFalse();

        // Validate the database is empty
        List<DetalleFactPres> detalleFactPresList = detalleFactPresRepository.findAll();
        assertThat(detalleFactPresList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDetalleFactPres() throws Exception {
        // Initialize the database
        detalleFactPresRepository.saveAndFlush(detalleFactPres);
        detalleFactPresSearchRepository.save(detalleFactPres);

        // Search the detalleFactPres
        restDetalleFactPresMockMvc.perform(get("/api/_search/detalle-fact-pres?query=id:" + detalleFactPres.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalleFactPres.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].predio").value(hasItem(DEFAULT_PREDIO.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalleFactPres.class);
        DetalleFactPres detalleFactPres1 = new DetalleFactPres();
        detalleFactPres1.setId(1L);
        DetalleFactPres detalleFactPres2 = new DetalleFactPres();
        detalleFactPres2.setId(detalleFactPres1.getId());
        assertThat(detalleFactPres1).isEqualTo(detalleFactPres2);
        detalleFactPres2.setId(2L);
        assertThat(detalleFactPres1).isNotEqualTo(detalleFactPres2);
        detalleFactPres1.setId(null);
        assertThat(detalleFactPres1).isNotEqualTo(detalleFactPres2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalleFactPresDTO.class);
        DetalleFactPresDTO detalleFactPresDTO1 = new DetalleFactPresDTO();
        detalleFactPresDTO1.setId(1L);
        DetalleFactPresDTO detalleFactPresDTO2 = new DetalleFactPresDTO();
        assertThat(detalleFactPresDTO1).isNotEqualTo(detalleFactPresDTO2);
        detalleFactPresDTO2.setId(detalleFactPresDTO1.getId());
        assertThat(detalleFactPresDTO1).isEqualTo(detalleFactPresDTO2);
        detalleFactPresDTO2.setId(2L);
        assertThat(detalleFactPresDTO1).isNotEqualTo(detalleFactPresDTO2);
        detalleFactPresDTO1.setId(null);
        assertThat(detalleFactPresDTO1).isNotEqualTo(detalleFactPresDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(detalleFactPresMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(detalleFactPresMapper.fromId(null)).isNull();
    }
}
