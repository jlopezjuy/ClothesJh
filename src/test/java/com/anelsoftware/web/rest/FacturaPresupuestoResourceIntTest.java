package com.anelsoftware.web.rest;

import com.anelsoftware.ClothesApp;

import com.anelsoftware.domain.FacturaPresupuesto;
import com.anelsoftware.repository.FacturaPresupuestoRepository;
import com.anelsoftware.service.FacturaPresupuestoService;
import com.anelsoftware.service.dto.FacturaPresupuestoDTO;
import com.anelsoftware.service.mapper.FacturaPresupuestoMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.anelsoftware.domain.enumeration.FormaPago;
/**
 * Test class for the FacturaPresupuestoResource REST controller.
 *
 * @see FacturaPresupuestoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClothesApp.class)
public class FacturaPresupuestoResourceIntTest {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final FormaPago DEFAULT_FORMA_PAGO = FormaPago.CONTADO;
    private static final FormaPago UPDATED_FORMA_PAGO = FormaPago.TARJETA;

    private static final BigDecimal DEFAULT_IMPORTE_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_IMPORTE_TOTAL = new BigDecimal(2);

    @Autowired
    private FacturaPresupuestoRepository facturaPresupuestoRepository;

    @Autowired
    private FacturaPresupuestoMapper facturaPresupuestoMapper;

    @Autowired
    private FacturaPresupuestoService facturaPresupuestoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFacturaPresupuestoMockMvc;

    private FacturaPresupuesto facturaPresupuesto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FacturaPresupuestoResource facturaPresupuestoResource = new FacturaPresupuestoResource(facturaPresupuestoService);
        this.restFacturaPresupuestoMockMvc = MockMvcBuilders.standaloneSetup(facturaPresupuestoResource)
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
    public static FacturaPresupuesto createEntity(EntityManager em) {
        FacturaPresupuesto facturaPresupuesto = new FacturaPresupuesto()
            .fecha(DEFAULT_FECHA)
            .formaPago(DEFAULT_FORMA_PAGO)
            .importeTotal(DEFAULT_IMPORTE_TOTAL);
        return facturaPresupuesto;
    }

    @Before
    public void initTest() {
        facturaPresupuesto = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacturaPresupuesto() throws Exception {
        int databaseSizeBeforeCreate = facturaPresupuestoRepository.findAll().size();

        // Create the FacturaPresupuesto
        FacturaPresupuestoDTO facturaPresupuestoDTO = facturaPresupuestoMapper.toDto(facturaPresupuesto);
        restFacturaPresupuestoMockMvc.perform(post("/api/factura-presupuestos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facturaPresupuestoDTO)))
            .andExpect(status().isCreated());

        // Validate the FacturaPresupuesto in the database
        List<FacturaPresupuesto> facturaPresupuestoList = facturaPresupuestoRepository.findAll();
        assertThat(facturaPresupuestoList).hasSize(databaseSizeBeforeCreate + 1);
        FacturaPresupuesto testFacturaPresupuesto = facturaPresupuestoList.get(facturaPresupuestoList.size() - 1);
        assertThat(testFacturaPresupuesto.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testFacturaPresupuesto.getFormaPago()).isEqualTo(DEFAULT_FORMA_PAGO);
        assertThat(testFacturaPresupuesto.getImporteTotal()).isEqualTo(DEFAULT_IMPORTE_TOTAL);
    }

    @Test
    @Transactional
    public void createFacturaPresupuestoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facturaPresupuestoRepository.findAll().size();

        // Create the FacturaPresupuesto with an existing ID
        facturaPresupuesto.setId(1L);
        FacturaPresupuestoDTO facturaPresupuestoDTO = facturaPresupuestoMapper.toDto(facturaPresupuesto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacturaPresupuestoMockMvc.perform(post("/api/factura-presupuestos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facturaPresupuestoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FacturaPresupuesto> facturaPresupuestoList = facturaPresupuestoRepository.findAll();
        assertThat(facturaPresupuestoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaPresupuestoRepository.findAll().size();
        // set the field null
        facturaPresupuesto.setFecha(null);

        // Create the FacturaPresupuesto, which fails.
        FacturaPresupuestoDTO facturaPresupuestoDTO = facturaPresupuestoMapper.toDto(facturaPresupuesto);

        restFacturaPresupuestoMockMvc.perform(post("/api/factura-presupuestos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facturaPresupuestoDTO)))
            .andExpect(status().isBadRequest());

        List<FacturaPresupuesto> facturaPresupuestoList = facturaPresupuestoRepository.findAll();
        assertThat(facturaPresupuestoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFormaPagoIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaPresupuestoRepository.findAll().size();
        // set the field null
        facturaPresupuesto.setFormaPago(null);

        // Create the FacturaPresupuesto, which fails.
        FacturaPresupuestoDTO facturaPresupuestoDTO = facturaPresupuestoMapper.toDto(facturaPresupuesto);

        restFacturaPresupuestoMockMvc.perform(post("/api/factura-presupuestos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facturaPresupuestoDTO)))
            .andExpect(status().isBadRequest());

        List<FacturaPresupuesto> facturaPresupuestoList = facturaPresupuestoRepository.findAll();
        assertThat(facturaPresupuestoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFacturaPresupuestos() throws Exception {
        // Initialize the database
        facturaPresupuestoRepository.saveAndFlush(facturaPresupuesto);

        // Get all the facturaPresupuestoList
        restFacturaPresupuestoMockMvc.perform(get("/api/factura-presupuestos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facturaPresupuesto.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].formaPago").value(hasItem(DEFAULT_FORMA_PAGO.toString())))
            .andExpect(jsonPath("$.[*].importeTotal").value(hasItem(DEFAULT_IMPORTE_TOTAL.intValue())));
    }

    @Test
    @Transactional
    public void getFacturaPresupuesto() throws Exception {
        // Initialize the database
        facturaPresupuestoRepository.saveAndFlush(facturaPresupuesto);

        // Get the facturaPresupuesto
        restFacturaPresupuestoMockMvc.perform(get("/api/factura-presupuestos/{id}", facturaPresupuesto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(facturaPresupuesto.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.formaPago").value(DEFAULT_FORMA_PAGO.toString()))
            .andExpect(jsonPath("$.importeTotal").value(DEFAULT_IMPORTE_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFacturaPresupuesto() throws Exception {
        // Get the facturaPresupuesto
        restFacturaPresupuestoMockMvc.perform(get("/api/factura-presupuestos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacturaPresupuesto() throws Exception {
        // Initialize the database
        facturaPresupuestoRepository.saveAndFlush(facturaPresupuesto);
        int databaseSizeBeforeUpdate = facturaPresupuestoRepository.findAll().size();

        // Update the facturaPresupuesto
        FacturaPresupuesto updatedFacturaPresupuesto = facturaPresupuestoRepository.findOne(facturaPresupuesto.getId());
        updatedFacturaPresupuesto
            .fecha(UPDATED_FECHA)
            .formaPago(UPDATED_FORMA_PAGO)
            .importeTotal(UPDATED_IMPORTE_TOTAL);
        FacturaPresupuestoDTO facturaPresupuestoDTO = facturaPresupuestoMapper.toDto(updatedFacturaPresupuesto);

        restFacturaPresupuestoMockMvc.perform(put("/api/factura-presupuestos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facturaPresupuestoDTO)))
            .andExpect(status().isOk());

        // Validate the FacturaPresupuesto in the database
        List<FacturaPresupuesto> facturaPresupuestoList = facturaPresupuestoRepository.findAll();
        assertThat(facturaPresupuestoList).hasSize(databaseSizeBeforeUpdate);
        FacturaPresupuesto testFacturaPresupuesto = facturaPresupuestoList.get(facturaPresupuestoList.size() - 1);
        assertThat(testFacturaPresupuesto.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testFacturaPresupuesto.getFormaPago()).isEqualTo(UPDATED_FORMA_PAGO);
        assertThat(testFacturaPresupuesto.getImporteTotal()).isEqualTo(UPDATED_IMPORTE_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingFacturaPresupuesto() throws Exception {
        int databaseSizeBeforeUpdate = facturaPresupuestoRepository.findAll().size();

        // Create the FacturaPresupuesto
        FacturaPresupuestoDTO facturaPresupuestoDTO = facturaPresupuestoMapper.toDto(facturaPresupuesto);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFacturaPresupuestoMockMvc.perform(put("/api/factura-presupuestos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facturaPresupuestoDTO)))
            .andExpect(status().isCreated());

        // Validate the FacturaPresupuesto in the database
        List<FacturaPresupuesto> facturaPresupuestoList = facturaPresupuestoRepository.findAll();
        assertThat(facturaPresupuestoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFacturaPresupuesto() throws Exception {
        // Initialize the database
        facturaPresupuestoRepository.saveAndFlush(facturaPresupuesto);
        int databaseSizeBeforeDelete = facturaPresupuestoRepository.findAll().size();

        // Get the facturaPresupuesto
        restFacturaPresupuestoMockMvc.perform(delete("/api/factura-presupuestos/{id}", facturaPresupuesto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FacturaPresupuesto> facturaPresupuestoList = facturaPresupuestoRepository.findAll();
        assertThat(facturaPresupuestoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacturaPresupuesto.class);
        FacturaPresupuesto facturaPresupuesto1 = new FacturaPresupuesto();
        facturaPresupuesto1.setId(1L);
        FacturaPresupuesto facturaPresupuesto2 = new FacturaPresupuesto();
        facturaPresupuesto2.setId(facturaPresupuesto1.getId());
        assertThat(facturaPresupuesto1).isEqualTo(facturaPresupuesto2);
        facturaPresupuesto2.setId(2L);
        assertThat(facturaPresupuesto1).isNotEqualTo(facturaPresupuesto2);
        facturaPresupuesto1.setId(null);
        assertThat(facturaPresupuesto1).isNotEqualTo(facturaPresupuesto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacturaPresupuestoDTO.class);
        FacturaPresupuestoDTO facturaPresupuestoDTO1 = new FacturaPresupuestoDTO();
        facturaPresupuestoDTO1.setId(1L);
        FacturaPresupuestoDTO facturaPresupuestoDTO2 = new FacturaPresupuestoDTO();
        assertThat(facturaPresupuestoDTO1).isNotEqualTo(facturaPresupuestoDTO2);
        facturaPresupuestoDTO2.setId(facturaPresupuestoDTO1.getId());
        assertThat(facturaPresupuestoDTO1).isEqualTo(facturaPresupuestoDTO2);
        facturaPresupuestoDTO2.setId(2L);
        assertThat(facturaPresupuestoDTO1).isNotEqualTo(facturaPresupuestoDTO2);
        facturaPresupuestoDTO1.setId(null);
        assertThat(facturaPresupuestoDTO1).isNotEqualTo(facturaPresupuestoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(facturaPresupuestoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(facturaPresupuestoMapper.fromId(null)).isNull();
    }
}
