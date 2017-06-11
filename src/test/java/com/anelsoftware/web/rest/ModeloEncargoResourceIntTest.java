package com.anelsoftware.web.rest;

import com.anelsoftware.ClothesApp;

import com.anelsoftware.domain.ModeloEncargo;
import com.anelsoftware.domain.Encargo;
import com.anelsoftware.repository.ModeloEncargoRepository;
import com.anelsoftware.service.dto.ModeloEncargoDTO;
import com.anelsoftware.service.mapper.ModeloEncargoMapper;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ModeloEncargoResource REST controller.
 *
 * @see ModeloEncargoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClothesApp.class)
public class ModeloEncargoResourceIntTest {

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NOMBRE_MODELO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_MODELO = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR_VESTIDO = "AAAAAAAAAA";
    private static final String UPDATED_COLOR_VESTIDO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BORDADO = false;
    private static final Boolean UPDATED_BORDADO = true;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACION = "BBBBBBBBBB";

    @Autowired
    private ModeloEncargoRepository modeloEncargoRepository;

    @Autowired
    private ModeloEncargoMapper modeloEncargoMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restModeloEncargoMockMvc;

    private ModeloEncargo modeloEncargo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ModeloEncargoResource modeloEncargoResource = new ModeloEncargoResource(modeloEncargoRepository, modeloEncargoMapper);
        this.restModeloEncargoMockMvc = MockMvcBuilders.standaloneSetup(modeloEncargoResource)
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
    public static ModeloEncargo createEntity(EntityManager em) {
        ModeloEncargo modeloEncargo = new ModeloEncargo()
            .imagen(DEFAULT_IMAGEN)
            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE)
            .nombreModelo(DEFAULT_NOMBRE_MODELO)
            .colorVestido(DEFAULT_COLOR_VESTIDO)
            .bordado(DEFAULT_BORDADO)
            .descripcion(DEFAULT_DESCRIPCION)
            .observacion(DEFAULT_OBSERVACION);
        // Add required entity
        Encargo encargo = EncargoResourceIntTest.createEntity(em);
        em.persist(encargo);
        em.flush();
        modeloEncargo.setEncargo(encargo);
        return modeloEncargo;
    }

    @Before
    public void initTest() {
        modeloEncargo = createEntity(em);
    }

    @Test
    @Transactional
    public void createModeloEncargo() throws Exception {
        int databaseSizeBeforeCreate = modeloEncargoRepository.findAll().size();

        // Create the ModeloEncargo
        ModeloEncargoDTO modeloEncargoDTO = modeloEncargoMapper.toDto(modeloEncargo);
        restModeloEncargoMockMvc.perform(post("/api/modelo-encargos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloEncargoDTO)))
            .andExpect(status().isCreated());

        // Validate the ModeloEncargo in the database
        List<ModeloEncargo> modeloEncargoList = modeloEncargoRepository.findAll();
        assertThat(modeloEncargoList).hasSize(databaseSizeBeforeCreate + 1);
        ModeloEncargo testModeloEncargo = modeloEncargoList.get(modeloEncargoList.size() - 1);
        assertThat(testModeloEncargo.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testModeloEncargo.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
        assertThat(testModeloEncargo.getNombreModelo()).isEqualTo(DEFAULT_NOMBRE_MODELO);
        assertThat(testModeloEncargo.getColorVestido()).isEqualTo(DEFAULT_COLOR_VESTIDO);
        assertThat(testModeloEncargo.isBordado()).isEqualTo(DEFAULT_BORDADO);
        assertThat(testModeloEncargo.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testModeloEncargo.getObservacion()).isEqualTo(DEFAULT_OBSERVACION);
    }

    @Test
    @Transactional
    public void createModeloEncargoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = modeloEncargoRepository.findAll().size();

        // Create the ModeloEncargo with an existing ID
        modeloEncargo.setId(1L);
        ModeloEncargoDTO modeloEncargoDTO = modeloEncargoMapper.toDto(modeloEncargo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModeloEncargoMockMvc.perform(post("/api/modelo-encargos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloEncargoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ModeloEncargo> modeloEncargoList = modeloEncargoRepository.findAll();
        assertThat(modeloEncargoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkImagenIsRequired() throws Exception {
        int databaseSizeBeforeTest = modeloEncargoRepository.findAll().size();
        // set the field null
        modeloEncargo.setImagen(null);

        // Create the ModeloEncargo, which fails.
        ModeloEncargoDTO modeloEncargoDTO = modeloEncargoMapper.toDto(modeloEncargo);

        restModeloEncargoMockMvc.perform(post("/api/modelo-encargos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloEncargoDTO)))
            .andExpect(status().isBadRequest());

        List<ModeloEncargo> modeloEncargoList = modeloEncargoRepository.findAll();
        assertThat(modeloEncargoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreModeloIsRequired() throws Exception {
        int databaseSizeBeforeTest = modeloEncargoRepository.findAll().size();
        // set the field null
        modeloEncargo.setNombreModelo(null);

        // Create the ModeloEncargo, which fails.
        ModeloEncargoDTO modeloEncargoDTO = modeloEncargoMapper.toDto(modeloEncargo);

        restModeloEncargoMockMvc.perform(post("/api/modelo-encargos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloEncargoDTO)))
            .andExpect(status().isBadRequest());

        List<ModeloEncargo> modeloEncargoList = modeloEncargoRepository.findAll();
        assertThat(modeloEncargoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkColorVestidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = modeloEncargoRepository.findAll().size();
        // set the field null
        modeloEncargo.setColorVestido(null);

        // Create the ModeloEncargo, which fails.
        ModeloEncargoDTO modeloEncargoDTO = modeloEncargoMapper.toDto(modeloEncargo);

        restModeloEncargoMockMvc.perform(post("/api/modelo-encargos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloEncargoDTO)))
            .andExpect(status().isBadRequest());

        List<ModeloEncargo> modeloEncargoList = modeloEncargoRepository.findAll();
        assertThat(modeloEncargoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllModeloEncargos() throws Exception {
        // Initialize the database
        modeloEncargoRepository.saveAndFlush(modeloEncargo);

        // Get all the modeloEncargoList
        restModeloEncargoMockMvc.perform(get("/api/modelo-encargos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modeloEncargo.getId().intValue())))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
            .andExpect(jsonPath("$.[*].nombreModelo").value(hasItem(DEFAULT_NOMBRE_MODELO.toString())))
            .andExpect(jsonPath("$.[*].colorVestido").value(hasItem(DEFAULT_COLOR_VESTIDO.toString())))
            .andExpect(jsonPath("$.[*].bordado").value(hasItem(DEFAULT_BORDADO.booleanValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].observacion").value(hasItem(DEFAULT_OBSERVACION.toString())));
    }

    @Test
    @Transactional
    public void getModeloEncargo() throws Exception {
        // Initialize the database
        modeloEncargoRepository.saveAndFlush(modeloEncargo);

        // Get the modeloEncargo
        restModeloEncargoMockMvc.perform(get("/api/modelo-encargos/{id}", modeloEncargo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(modeloEncargo.getId().intValue()))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)))
            .andExpect(jsonPath("$.nombreModelo").value(DEFAULT_NOMBRE_MODELO.toString()))
            .andExpect(jsonPath("$.colorVestido").value(DEFAULT_COLOR_VESTIDO.toString()))
            .andExpect(jsonPath("$.bordado").value(DEFAULT_BORDADO.booleanValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.observacion").value(DEFAULT_OBSERVACION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingModeloEncargo() throws Exception {
        // Get the modeloEncargo
        restModeloEncargoMockMvc.perform(get("/api/modelo-encargos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModeloEncargo() throws Exception {
        // Initialize the database
        modeloEncargoRepository.saveAndFlush(modeloEncargo);
        int databaseSizeBeforeUpdate = modeloEncargoRepository.findAll().size();

        // Update the modeloEncargo
        ModeloEncargo updatedModeloEncargo = modeloEncargoRepository.findOne(modeloEncargo.getId());
        updatedModeloEncargo
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .nombreModelo(UPDATED_NOMBRE_MODELO)
            .colorVestido(UPDATED_COLOR_VESTIDO)
            .bordado(UPDATED_BORDADO)
            .descripcion(UPDATED_DESCRIPCION)
            .observacion(UPDATED_OBSERVACION);
        ModeloEncargoDTO modeloEncargoDTO = modeloEncargoMapper.toDto(updatedModeloEncargo);

        restModeloEncargoMockMvc.perform(put("/api/modelo-encargos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloEncargoDTO)))
            .andExpect(status().isOk());

        // Validate the ModeloEncargo in the database
        List<ModeloEncargo> modeloEncargoList = modeloEncargoRepository.findAll();
        assertThat(modeloEncargoList).hasSize(databaseSizeBeforeUpdate);
        ModeloEncargo testModeloEncargo = modeloEncargoList.get(modeloEncargoList.size() - 1);
        assertThat(testModeloEncargo.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testModeloEncargo.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
        assertThat(testModeloEncargo.getNombreModelo()).isEqualTo(UPDATED_NOMBRE_MODELO);
        assertThat(testModeloEncargo.getColorVestido()).isEqualTo(UPDATED_COLOR_VESTIDO);
        assertThat(testModeloEncargo.isBordado()).isEqualTo(UPDATED_BORDADO);
        assertThat(testModeloEncargo.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testModeloEncargo.getObservacion()).isEqualTo(UPDATED_OBSERVACION);
    }

    @Test
    @Transactional
    public void updateNonExistingModeloEncargo() throws Exception {
        int databaseSizeBeforeUpdate = modeloEncargoRepository.findAll().size();

        // Create the ModeloEncargo
        ModeloEncargoDTO modeloEncargoDTO = modeloEncargoMapper.toDto(modeloEncargo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restModeloEncargoMockMvc.perform(put("/api/modelo-encargos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloEncargoDTO)))
            .andExpect(status().isCreated());

        // Validate the ModeloEncargo in the database
        List<ModeloEncargo> modeloEncargoList = modeloEncargoRepository.findAll();
        assertThat(modeloEncargoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteModeloEncargo() throws Exception {
        // Initialize the database
        modeloEncargoRepository.saveAndFlush(modeloEncargo);
        int databaseSizeBeforeDelete = modeloEncargoRepository.findAll().size();

        // Get the modeloEncargo
        restModeloEncargoMockMvc.perform(delete("/api/modelo-encargos/{id}", modeloEncargo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ModeloEncargo> modeloEncargoList = modeloEncargoRepository.findAll();
        assertThat(modeloEncargoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModeloEncargo.class);
        ModeloEncargo modeloEncargo1 = new ModeloEncargo();
        modeloEncargo1.setId(1L);
        ModeloEncargo modeloEncargo2 = new ModeloEncargo();
        modeloEncargo2.setId(modeloEncargo1.getId());
        assertThat(modeloEncargo1).isEqualTo(modeloEncargo2);
        modeloEncargo2.setId(2L);
        assertThat(modeloEncargo1).isNotEqualTo(modeloEncargo2);
        modeloEncargo1.setId(null);
        assertThat(modeloEncargo1).isNotEqualTo(modeloEncargo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModeloEncargoDTO.class);
        ModeloEncargoDTO modeloEncargoDTO1 = new ModeloEncargoDTO();
        modeloEncargoDTO1.setId(1L);
        ModeloEncargoDTO modeloEncargoDTO2 = new ModeloEncargoDTO();
        assertThat(modeloEncargoDTO1).isNotEqualTo(modeloEncargoDTO2);
        modeloEncargoDTO2.setId(modeloEncargoDTO1.getId());
        assertThat(modeloEncargoDTO1).isEqualTo(modeloEncargoDTO2);
        modeloEncargoDTO2.setId(2L);
        assertThat(modeloEncargoDTO1).isNotEqualTo(modeloEncargoDTO2);
        modeloEncargoDTO1.setId(null);
        assertThat(modeloEncargoDTO1).isNotEqualTo(modeloEncargoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(modeloEncargoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(modeloEncargoMapper.fromId(null)).isNull();
    }
}
