package com.anelsoftware.web.rest;

import com.anelsoftware.ClothesApp;

import com.anelsoftware.domain.Proveedor;
import com.anelsoftware.repository.ProveedorRepository;
import com.anelsoftware.service.ProveedorService;
import com.anelsoftware.repository.search.ProveedorSearchRepository;
import com.anelsoftware.service.dto.ProveedorDTO;
import com.anelsoftware.service.mapper.ProveedorMapper;
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
 * Test class for the ProveedorResource REST controller.
 *
 * @see ProveedorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClothesApp.class)
public class ProveedorResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DOMICILIO = "AAAAAAAAAA";
    private static final String UPDATED_DOMICILIO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_CUIL_CUIT = "AAAAAAAAAA";
    private static final String UPDATED_CUIL_CUIT = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CELULAR = "AAAAAAAAAA";
    private static final String UPDATED_CELULAR = "BBBBBBBBBB";

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ProveedorMapper proveedorMapper;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProveedorSearchRepository proveedorSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProveedorMockMvc;

    private Proveedor proveedor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProveedorResource proveedorResource = new ProveedorResource(proveedorService);
        this.restProveedorMockMvc = MockMvcBuilders.standaloneSetup(proveedorResource)
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
    public static Proveedor createEntity(EntityManager em) {
        Proveedor proveedor = new Proveedor()
            .nombre(DEFAULT_NOMBRE)
            .domicilio(DEFAULT_DOMICILIO)
            .telefono(DEFAULT_TELEFONO)
            .cuilCuit(DEFAULT_CUIL_CUIT)
            .email(DEFAULT_EMAIL)
            .celular(DEFAULT_CELULAR);
        return proveedor;
    }

    @Before
    public void initTest() {
        proveedorSearchRepository.deleteAll();
        proveedor = createEntity(em);
    }

    @Test
    @Transactional
    public void createProveedor() throws Exception {
        int databaseSizeBeforeCreate = proveedorRepository.findAll().size();

        // Create the Proveedor
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);
        restProveedorMockMvc.perform(post("/api/proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isCreated());

        // Validate the Proveedor in the database
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeCreate + 1);
        Proveedor testProveedor = proveedorList.get(proveedorList.size() - 1);
        assertThat(testProveedor.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testProveedor.getDomicilio()).isEqualTo(DEFAULT_DOMICILIO);
        assertThat(testProveedor.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testProveedor.getCuilCuit()).isEqualTo(DEFAULT_CUIL_CUIT);
        assertThat(testProveedor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProveedor.getCelular()).isEqualTo(DEFAULT_CELULAR);

        // Validate the Proveedor in Elasticsearch
        Proveedor proveedorEs = proveedorSearchRepository.findOne(testProveedor.getId());
        assertThat(proveedorEs).isEqualToComparingFieldByField(testProveedor);
    }

    @Test
    @Transactional
    public void createProveedorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = proveedorRepository.findAll().size();

        // Create the Proveedor with an existing ID
        proveedor.setId(1L);
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProveedorMockMvc.perform(post("/api/proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorRepository.findAll().size();
        // set the field null
        proveedor.setNombre(null);

        // Create the Proveedor, which fails.
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        restProveedorMockMvc.perform(post("/api/proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isBadRequest());

        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDomicilioIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorRepository.findAll().size();
        // set the field null
        proveedor.setDomicilio(null);

        // Create the Proveedor, which fails.
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        restProveedorMockMvc.perform(post("/api/proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isBadRequest());

        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorRepository.findAll().size();
        // set the field null
        proveedor.setTelefono(null);

        // Create the Proveedor, which fails.
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        restProveedorMockMvc.perform(post("/api/proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isBadRequest());

        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCuilCuitIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorRepository.findAll().size();
        // set the field null
        proveedor.setCuilCuit(null);

        // Create the Proveedor, which fails.
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        restProveedorMockMvc.perform(post("/api/proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isBadRequest());

        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorRepository.findAll().size();
        // set the field null
        proveedor.setEmail(null);

        // Create the Proveedor, which fails.
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        restProveedorMockMvc.perform(post("/api/proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isBadRequest());

        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCelularIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorRepository.findAll().size();
        // set the field null
        proveedor.setCelular(null);

        // Create the Proveedor, which fails.
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        restProveedorMockMvc.perform(post("/api/proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isBadRequest());

        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProveedors() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);

        // Get all the proveedorList
        restProveedorMockMvc.perform(get("/api/proveedors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proveedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].domicilio").value(hasItem(DEFAULT_DOMICILIO.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].cuilCuit").value(hasItem(DEFAULT_CUIL_CUIT.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR.toString())));
    }

    @Test
    @Transactional
    public void getProveedor() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);

        // Get the proveedor
        restProveedorMockMvc.perform(get("/api/proveedors/{id}", proveedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(proveedor.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.domicilio").value(DEFAULT_DOMICILIO.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.cuilCuit").value(DEFAULT_CUIL_CUIT.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.celular").value(DEFAULT_CELULAR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProveedor() throws Exception {
        // Get the proveedor
        restProveedorMockMvc.perform(get("/api/proveedors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProveedor() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);
        proveedorSearchRepository.save(proveedor);
        int databaseSizeBeforeUpdate = proveedorRepository.findAll().size();

        // Update the proveedor
        Proveedor updatedProveedor = proveedorRepository.findOne(proveedor.getId());
        updatedProveedor
            .nombre(UPDATED_NOMBRE)
            .domicilio(UPDATED_DOMICILIO)
            .telefono(UPDATED_TELEFONO)
            .cuilCuit(UPDATED_CUIL_CUIT)
            .email(UPDATED_EMAIL)
            .celular(UPDATED_CELULAR);
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(updatedProveedor);

        restProveedorMockMvc.perform(put("/api/proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isOk());

        // Validate the Proveedor in the database
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeUpdate);
        Proveedor testProveedor = proveedorList.get(proveedorList.size() - 1);
        assertThat(testProveedor.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProveedor.getDomicilio()).isEqualTo(UPDATED_DOMICILIO);
        assertThat(testProveedor.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testProveedor.getCuilCuit()).isEqualTo(UPDATED_CUIL_CUIT);
        assertThat(testProveedor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProveedor.getCelular()).isEqualTo(UPDATED_CELULAR);

        // Validate the Proveedor in Elasticsearch
        Proveedor proveedorEs = proveedorSearchRepository.findOne(testProveedor.getId());
        assertThat(proveedorEs).isEqualToComparingFieldByField(testProveedor);
    }

    @Test
    @Transactional
    public void updateNonExistingProveedor() throws Exception {
        int databaseSizeBeforeUpdate = proveedorRepository.findAll().size();

        // Create the Proveedor
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProveedorMockMvc.perform(put("/api/proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isCreated());

        // Validate the Proveedor in the database
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProveedor() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);
        proveedorSearchRepository.save(proveedor);
        int databaseSizeBeforeDelete = proveedorRepository.findAll().size();

        // Get the proveedor
        restProveedorMockMvc.perform(delete("/api/proveedors/{id}", proveedor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean proveedorExistsInEs = proveedorSearchRepository.exists(proveedor.getId());
        assertThat(proveedorExistsInEs).isFalse();

        // Validate the database is empty
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProveedor() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);
        proveedorSearchRepository.save(proveedor);

        // Search the proveedor
        restProveedorMockMvc.perform(get("/api/_search/proveedors?query=id:" + proveedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proveedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].domicilio").value(hasItem(DEFAULT_DOMICILIO.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].cuilCuit").value(hasItem(DEFAULT_CUIL_CUIT.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Proveedor.class);
        Proveedor proveedor1 = new Proveedor();
        proveedor1.setId(1L);
        Proveedor proveedor2 = new Proveedor();
        proveedor2.setId(proveedor1.getId());
        assertThat(proveedor1).isEqualTo(proveedor2);
        proveedor2.setId(2L);
        assertThat(proveedor1).isNotEqualTo(proveedor2);
        proveedor1.setId(null);
        assertThat(proveedor1).isNotEqualTo(proveedor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProveedorDTO.class);
        ProveedorDTO proveedorDTO1 = new ProveedorDTO();
        proveedorDTO1.setId(1L);
        ProveedorDTO proveedorDTO2 = new ProveedorDTO();
        assertThat(proveedorDTO1).isNotEqualTo(proveedorDTO2);
        proveedorDTO2.setId(proveedorDTO1.getId());
        assertThat(proveedorDTO1).isEqualTo(proveedorDTO2);
        proveedorDTO2.setId(2L);
        assertThat(proveedorDTO1).isNotEqualTo(proveedorDTO2);
        proveedorDTO1.setId(null);
        assertThat(proveedorDTO1).isNotEqualTo(proveedorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(proveedorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(proveedorMapper.fromId(null)).isNull();
    }
}
