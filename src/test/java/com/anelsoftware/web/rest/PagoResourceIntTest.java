package com.anelsoftware.web.rest;

import com.anelsoftware.ClothesApp;

import com.anelsoftware.domain.Pago;
import com.anelsoftware.domain.Encargo;
import com.anelsoftware.repository.PagoRepository;
import com.anelsoftware.service.PagoService;
import com.anelsoftware.service.dto.PagoDTO;
import com.anelsoftware.service.mapper.PagoMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PagoResource REST controller.
 *
 * @see PagoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClothesApp.class)
public class PagoResourceIntTest {

    private static final LocalDate DEFAULT_FECHA_PAGO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_PAGO = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_IMPORTE = 1D;
    private static final Double UPDATED_IMPORTE = 2D;

    private static final String DEFAULT_DETALLE = "AAAAAAAAAA";
    private static final String UPDATED_DETALLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_RECIBO = 1;
    private static final Integer UPDATED_NUMERO_RECIBO = 2;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private PagoMapper pagoMapper;

    @Autowired
    private PagoService pagoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPagoMockMvc;

    private Pago pago;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PagoResource pagoResource = new PagoResource(pagoService);
        this.restPagoMockMvc = MockMvcBuilders.standaloneSetup(pagoResource)
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
    public static Pago createEntity(EntityManager em) {
        Pago pago = new Pago()
            .fechaPago(DEFAULT_FECHA_PAGO)
            .importe(DEFAULT_IMPORTE)
            .detalle(DEFAULT_DETALLE)
            .numeroRecibo(DEFAULT_NUMERO_RECIBO);
        // Add required entity
        Encargo encargo = EncargoResourceIntTest.createEntity(em);
        em.persist(encargo);
        em.flush();
        pago.setEncargo(encargo);
        return pago;
    }

    @Before
    public void initTest() {
        pago = createEntity(em);
    }

    @Test
    @Transactional
    public void createPago() throws Exception {
        int databaseSizeBeforeCreate = pagoRepository.findAll().size();

        // Create the Pago
        PagoDTO pagoDTO = pagoMapper.toDto(pago);
        restPagoMockMvc.perform(post("/api/pagos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagoDTO)))
            .andExpect(status().isCreated());

        // Validate the Pago in the database
        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeCreate + 1);
        Pago testPago = pagoList.get(pagoList.size() - 1);
        assertThat(testPago.getFechaPago()).isEqualTo(DEFAULT_FECHA_PAGO);
        assertThat(testPago.getImporte()).isEqualTo(DEFAULT_IMPORTE);
        assertThat(testPago.getDetalle()).isEqualTo(DEFAULT_DETALLE);
        assertThat(testPago.getNumeroRecibo()).isEqualTo(DEFAULT_NUMERO_RECIBO);
    }

    @Test
    @Transactional
    public void createPagoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pagoRepository.findAll().size();

        // Create the Pago with an existing ID
        pago.setId(1L);
        PagoDTO pagoDTO = pagoMapper.toDto(pago);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPagoMockMvc.perform(post("/api/pagos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkImporteIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagoRepository.findAll().size();
        // set the field null
        pago.setImporte(null);

        // Create the Pago, which fails.
        PagoDTO pagoDTO = pagoMapper.toDto(pago);

        restPagoMockMvc.perform(post("/api/pagos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagoDTO)))
            .andExpect(status().isBadRequest());

        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDetalleIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagoRepository.findAll().size();
        // set the field null
        pago.setDetalle(null);

        // Create the Pago, which fails.
        PagoDTO pagoDTO = pagoMapper.toDto(pago);

        restPagoMockMvc.perform(post("/api/pagos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagoDTO)))
            .andExpect(status().isBadRequest());

        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroReciboIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagoRepository.findAll().size();
        // set the field null
        pago.setNumeroRecibo(null);

        // Create the Pago, which fails.
        PagoDTO pagoDTO = pagoMapper.toDto(pago);

        restPagoMockMvc.perform(post("/api/pagos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagoDTO)))
            .andExpect(status().isBadRequest());

        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPagos() throws Exception {
        // Initialize the database
        pagoRepository.saveAndFlush(pago);

        // Get all the pagoList
        restPagoMockMvc.perform(get("/api/pagos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pago.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaPago").value(hasItem(DEFAULT_FECHA_PAGO.toString())))
            .andExpect(jsonPath("$.[*].importe").value(hasItem(DEFAULT_IMPORTE.doubleValue())))
            .andExpect(jsonPath("$.[*].detalle").value(hasItem(DEFAULT_DETALLE.toString())))
            .andExpect(jsonPath("$.[*].numeroRecibo").value(hasItem(DEFAULT_NUMERO_RECIBO)));
    }

    @Test
    @Transactional
    public void getPago() throws Exception {
        // Initialize the database
        pagoRepository.saveAndFlush(pago);

        // Get the pago
        restPagoMockMvc.perform(get("/api/pagos/{id}", pago.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pago.getId().intValue()))
            .andExpect(jsonPath("$.fechaPago").value(DEFAULT_FECHA_PAGO.toString()))
            .andExpect(jsonPath("$.importe").value(DEFAULT_IMPORTE.doubleValue()))
            .andExpect(jsonPath("$.detalle").value(DEFAULT_DETALLE.toString()))
            .andExpect(jsonPath("$.numeroRecibo").value(DEFAULT_NUMERO_RECIBO));
    }

    @Test
    @Transactional
    public void getNonExistingPago() throws Exception {
        // Get the pago
        restPagoMockMvc.perform(get("/api/pagos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePago() throws Exception {
        // Initialize the database
        pagoRepository.saveAndFlush(pago);
        int databaseSizeBeforeUpdate = pagoRepository.findAll().size();

        // Update the pago
        Pago updatedPago = pagoRepository.findOne(pago.getId());
        updatedPago
            .fechaPago(UPDATED_FECHA_PAGO)
            .importe(UPDATED_IMPORTE)
            .detalle(UPDATED_DETALLE)
            .numeroRecibo(UPDATED_NUMERO_RECIBO);
        PagoDTO pagoDTO = pagoMapper.toDto(updatedPago);

        restPagoMockMvc.perform(put("/api/pagos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagoDTO)))
            .andExpect(status().isOk());

        // Validate the Pago in the database
        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeUpdate);
        Pago testPago = pagoList.get(pagoList.size() - 1);
        assertThat(testPago.getFechaPago()).isEqualTo(UPDATED_FECHA_PAGO);
        assertThat(testPago.getImporte()).isEqualTo(UPDATED_IMPORTE);
        assertThat(testPago.getDetalle()).isEqualTo(UPDATED_DETALLE);
        assertThat(testPago.getNumeroRecibo()).isEqualTo(UPDATED_NUMERO_RECIBO);
    }

    @Test
    @Transactional
    public void updateNonExistingPago() throws Exception {
        int databaseSizeBeforeUpdate = pagoRepository.findAll().size();

        // Create the Pago
        PagoDTO pagoDTO = pagoMapper.toDto(pago);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPagoMockMvc.perform(put("/api/pagos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagoDTO)))
            .andExpect(status().isCreated());

        // Validate the Pago in the database
        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePago() throws Exception {
        // Initialize the database
        pagoRepository.saveAndFlush(pago);
        int databaseSizeBeforeDelete = pagoRepository.findAll().size();

        // Get the pago
        restPagoMockMvc.perform(delete("/api/pagos/{id}", pago.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pago.class);
        Pago pago1 = new Pago();
        pago1.setId(1L);
        Pago pago2 = new Pago();
        pago2.setId(pago1.getId());
        assertThat(pago1).isEqualTo(pago2);
        pago2.setId(2L);
        assertThat(pago1).isNotEqualTo(pago2);
        pago1.setId(null);
        assertThat(pago1).isNotEqualTo(pago2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PagoDTO.class);
        PagoDTO pagoDTO1 = new PagoDTO();
        pagoDTO1.setId(1L);
        PagoDTO pagoDTO2 = new PagoDTO();
        assertThat(pagoDTO1).isNotEqualTo(pagoDTO2);
        pagoDTO2.setId(pagoDTO1.getId());
        assertThat(pagoDTO1).isEqualTo(pagoDTO2);
        pagoDTO2.setId(2L);
        assertThat(pagoDTO1).isNotEqualTo(pagoDTO2);
        pagoDTO1.setId(null);
        assertThat(pagoDTO1).isNotEqualTo(pagoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pagoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pagoMapper.fromId(null)).isNull();
    }
}
