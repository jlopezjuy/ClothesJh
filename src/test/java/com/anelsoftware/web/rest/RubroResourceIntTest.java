package com.anelsoftware.web.rest;

import com.anelsoftware.ClothesApp;

import com.anelsoftware.domain.Rubro;
import com.anelsoftware.repository.RubroRepository;
import com.anelsoftware.service.RubroService;
import com.anelsoftware.service.dto.RubroDTO;
import com.anelsoftware.service.mapper.RubroMapper;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RubroResource REST controller.
 *
 * @see RubroResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClothesApp.class)
public class RubroResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private RubroRepository rubroRepository;

    @Autowired
    private RubroMapper rubroMapper;

    @Autowired
    private RubroService rubroService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRubroMockMvc;

    private Rubro rubro;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RubroResource rubroResource = new RubroResource(rubroService);
        this.restRubroMockMvc = MockMvcBuilders.standaloneSetup(rubroResource)
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
    public static Rubro createEntity(EntityManager em) {
        Rubro rubro = new Rubro()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION);
        return rubro;
    }

    @Before
    public void initTest() {
        rubro = createEntity(em);
    }

    @Test
    @Transactional
    public void createRubro() throws Exception {
        int databaseSizeBeforeCreate = rubroRepository.findAll().size();

        // Create the Rubro
        RubroDTO rubroDTO = rubroMapper.toDto(rubro);
        restRubroMockMvc.perform(post("/api/rubros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubroDTO)))
            .andExpect(status().isCreated());

        // Validate the Rubro in the database
        List<Rubro> rubroList = rubroRepository.findAll();
        assertThat(rubroList).hasSize(databaseSizeBeforeCreate + 1);
        Rubro testRubro = rubroList.get(rubroList.size() - 1);
        assertThat(testRubro.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testRubro.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createRubroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rubroRepository.findAll().size();

        // Create the Rubro with an existing ID
        rubro.setId(1L);
        RubroDTO rubroDTO = rubroMapper.toDto(rubro);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRubroMockMvc.perform(post("/api/rubros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Rubro> rubroList = rubroRepository.findAll();
        assertThat(rubroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRubros() throws Exception {
        // Initialize the database
        rubroRepository.saveAndFlush(rubro);

        // Get all the rubroList
        restRubroMockMvc.perform(get("/api/rubros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rubro.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getRubro() throws Exception {
        // Initialize the database
        rubroRepository.saveAndFlush(rubro);

        // Get the rubro
        restRubroMockMvc.perform(get("/api/rubros/{id}", rubro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rubro.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRubro() throws Exception {
        // Get the rubro
        restRubroMockMvc.perform(get("/api/rubros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRubro() throws Exception {
        // Initialize the database
        rubroRepository.saveAndFlush(rubro);
        int databaseSizeBeforeUpdate = rubroRepository.findAll().size();

        // Update the rubro
        Rubro updatedRubro = rubroRepository.findOne(rubro.getId());
        updatedRubro
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION);
        RubroDTO rubroDTO = rubroMapper.toDto(updatedRubro);

        restRubroMockMvc.perform(put("/api/rubros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubroDTO)))
            .andExpect(status().isOk());

        // Validate the Rubro in the database
        List<Rubro> rubroList = rubroRepository.findAll();
        assertThat(rubroList).hasSize(databaseSizeBeforeUpdate);
        Rubro testRubro = rubroList.get(rubroList.size() - 1);
        assertThat(testRubro.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testRubro.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingRubro() throws Exception {
        int databaseSizeBeforeUpdate = rubroRepository.findAll().size();

        // Create the Rubro
        RubroDTO rubroDTO = rubroMapper.toDto(rubro);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRubroMockMvc.perform(put("/api/rubros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubroDTO)))
            .andExpect(status().isCreated());

        // Validate the Rubro in the database
        List<Rubro> rubroList = rubroRepository.findAll();
        assertThat(rubroList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRubro() throws Exception {
        // Initialize the database
        rubroRepository.saveAndFlush(rubro);
        int databaseSizeBeforeDelete = rubroRepository.findAll().size();

        // Get the rubro
        restRubroMockMvc.perform(delete("/api/rubros/{id}", rubro.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Rubro> rubroList = rubroRepository.findAll();
        assertThat(rubroList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rubro.class);
        Rubro rubro1 = new Rubro();
        rubro1.setId(1L);
        Rubro rubro2 = new Rubro();
        rubro2.setId(rubro1.getId());
        assertThat(rubro1).isEqualTo(rubro2);
        rubro2.setId(2L);
        assertThat(rubro1).isNotEqualTo(rubro2);
        rubro1.setId(null);
        assertThat(rubro1).isNotEqualTo(rubro2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RubroDTO.class);
        RubroDTO rubroDTO1 = new RubroDTO();
        rubroDTO1.setId(1L);
        RubroDTO rubroDTO2 = new RubroDTO();
        assertThat(rubroDTO1).isNotEqualTo(rubroDTO2);
        rubroDTO2.setId(rubroDTO1.getId());
        assertThat(rubroDTO1).isEqualTo(rubroDTO2);
        rubroDTO2.setId(2L);
        assertThat(rubroDTO1).isNotEqualTo(rubroDTO2);
        rubroDTO1.setId(null);
        assertThat(rubroDTO1).isNotEqualTo(rubroDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(rubroMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(rubroMapper.fromId(null)).isNull();
    }
}
