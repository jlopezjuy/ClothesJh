package com.anelsoftware.web.rest;

import com.anelsoftware.ClothesApp;

import com.anelsoftware.domain.Medida;
import com.anelsoftware.domain.Cliente;
import com.anelsoftware.repository.MedidaRepository;
import com.anelsoftware.service.MedidaService;
import com.anelsoftware.service.dto.MedidaDTO;
import com.anelsoftware.service.mapper.MedidaMapper;
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

import com.anelsoftware.domain.enumeration.TipoFalda;
/**
 * Test class for the MedidaResource REST controller.
 *
 * @see MedidaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClothesApp.class)
public class MedidaResourceIntTest {

    private static final Double DEFAULT_CONTORNO_BUSTO = 1D;
    private static final Double UPDATED_CONTORNO_BUSTO = 2D;

    private static final Double DEFAULT_ANCHO_PECHO = 1D;
    private static final Double UPDATED_ANCHO_PECHO = 2D;

    private static final Double DEFAULT_ALTO_BUSTO = 1D;
    private static final Double UPDATED_ALTO_BUSTO = 2D;

    private static final Double DEFAULT_BAJO_BUSTO = 1D;
    private static final Double UPDATED_BAJO_BUSTO = 2D;

    private static final Double DEFAULT_ALTURA_PINZA = 1D;
    private static final Double UPDATED_ALTURA_PINZA = 2D;

    private static final Double DEFAULT_SEPARACION_BUSTO = 1D;
    private static final Double UPDATED_SEPARACION_BUSTO = 2D;

    private static final Double DEFAULT_TALLE_DELTANTERO = 1D;
    private static final Double UPDATED_TALLE_DELTANTERO = 2D;

    private static final Double DEFAULT_TALLE_ESPALDA = 1D;
    private static final Double UPDATED_TALLE_ESPALDA = 2D;

    private static final Double DEFAULT_LARGO_CORSET = 1D;
    private static final Double UPDATED_LARGO_CORSET = 2D;

    private static final Double DEFAULT_COSTADO = 1D;
    private static final Double UPDATED_COSTADO = 2D;

    private static final Double DEFAULT_HOMBRO = 1D;
    private static final Double UPDATED_HOMBRO = 2D;

    private static final Double DEFAULT_ANCHO_HOMBRO = 1D;
    private static final Double UPDATED_ANCHO_HOMBRO = 2D;

    private static final Double DEFAULT_LARGO_MANGA = 1D;
    private static final Double UPDATED_LARGO_MANGA = 2D;

    private static final Double DEFAULT_SISA_DELANTERO = 1D;
    private static final Double UPDATED_SISA_DELANTERO = 2D;

    private static final Double DEFAULT_SISA_ESPALDA = 1D;
    private static final Double UPDATED_SISA_ESPALDA = 2D;

    private static final Double DEFAULT_CONTORNO_CINTURA = 1D;
    private static final Double UPDATED_CONTORNO_CINTURA = 2D;

    private static final Double DEFAULT_ANTE_CADERA = 1D;
    private static final Double UPDATED_ANTE_CADERA = 2D;

    private static final Double DEFAULT_CONTORNO_CADERA = 1D;
    private static final Double UPDATED_CONTORNO_CADERA = 2D;

    private static final Double DEFAULT_POSICION_CADERA = 1D;
    private static final Double UPDATED_POSICION_CADERA = 2D;

    private static final Double DEFAULT_LARGO_FALDA = 1D;
    private static final Double UPDATED_LARGO_FALDA = 2D;

    private static final TipoFalda DEFAULT_TIPO_FALDA = TipoFalda.TUBO;
    private static final TipoFalda UPDATED_TIPO_FALDA = TipoFalda.RECTA;

    private static final LocalDate DEFAULT_FECHA_MEDIDA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MEDIDA = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_ANCHO_ESPALDA = 1D;
    private static final Double UPDATED_ANCHO_ESPALDA = 2D;

    private static final Double DEFAULT_ANCHO_MANGA = 1D;
    private static final Double UPDATED_ANCHO_MANGA = 2D;

    private static final Double DEFAULT_TIRO_PANTALON = 1D;
    private static final Double UPDATED_TIRO_PANTALON = 2D;

    private static final Double DEFAULT_ANCHO_PINZA_PANTALON = 1D;
    private static final Double UPDATED_ANCHO_PINZA_PANTALON = 2D;

    private static final Double DEFAULT_ANCHO_RODILLA_PANTALON = 1D;
    private static final Double UPDATED_ANCHO_RODILLA_PANTALON = 2D;

    private static final Double DEFAULT_BOTA_PANTALON = 1D;
    private static final Double UPDATED_BOTA_PANTALON = 2D;

    private static final Double DEFAULT_LARGO_PANTALON = 1D;
    private static final Double UPDATED_LARGO_PANTALON = 2D;

    @Autowired
    private MedidaRepository medidaRepository;

    @Autowired
    private MedidaMapper medidaMapper;

    @Autowired
    private MedidaService medidaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMedidaMockMvc;

    private Medida medida;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MedidaResource medidaResource = new MedidaResource(medidaService);
        this.restMedidaMockMvc = MockMvcBuilders.standaloneSetup(medidaResource)
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
    public static Medida createEntity(EntityManager em) {
        Medida medida = new Medida()
            .contornoBusto(DEFAULT_CONTORNO_BUSTO)
            .anchoPecho(DEFAULT_ANCHO_PECHO)
            .altoBusto(DEFAULT_ALTO_BUSTO)
            .bajoBusto(DEFAULT_BAJO_BUSTO)
            .alturaPinza(DEFAULT_ALTURA_PINZA)
            .separacionBusto(DEFAULT_SEPARACION_BUSTO)
            .talleDeltantero(DEFAULT_TALLE_DELTANTERO)
            .talleEspalda(DEFAULT_TALLE_ESPALDA)
            .largoCorset(DEFAULT_LARGO_CORSET)
            .costado(DEFAULT_COSTADO)
            .hombro(DEFAULT_HOMBRO)
            .anchoHombro(DEFAULT_ANCHO_HOMBRO)
            .largoManga(DEFAULT_LARGO_MANGA)
            .sisaDelantero(DEFAULT_SISA_DELANTERO)
            .sisaEspalda(DEFAULT_SISA_ESPALDA)
            .contornoCintura(DEFAULT_CONTORNO_CINTURA)
            .anteCadera(DEFAULT_ANTE_CADERA)
            .contornoCadera(DEFAULT_CONTORNO_CADERA)
            .posicionCadera(DEFAULT_POSICION_CADERA)
            .largoFalda(DEFAULT_LARGO_FALDA)
            .tipoFalda(DEFAULT_TIPO_FALDA)
            .fechaMedida(DEFAULT_FECHA_MEDIDA)
            .anchoEspalda(DEFAULT_ANCHO_ESPALDA)
            .anchoManga(DEFAULT_ANCHO_MANGA)
            .tiroPantalon(DEFAULT_TIRO_PANTALON)
            .anchoPinzaPantalon(DEFAULT_ANCHO_PINZA_PANTALON)
            .anchoRodillaPantalon(DEFAULT_ANCHO_RODILLA_PANTALON)
            .botaPantalon(DEFAULT_BOTA_PANTALON)
            .largoPantalon(DEFAULT_LARGO_PANTALON);
        // Add required entity
        Cliente cliente = ClienteResourceIntTest.createEntity(em);
        em.persist(cliente);
        em.flush();
        medida.setCliente(cliente);
        return medida;
    }

    @Before
    public void initTest() {
        medida = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedida() throws Exception {
        int databaseSizeBeforeCreate = medidaRepository.findAll().size();

        // Create the Medida
        MedidaDTO medidaDTO = medidaMapper.toDto(medida);
        restMedidaMockMvc.perform(post("/api/medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medidaDTO)))
            .andExpect(status().isCreated());

        // Validate the Medida in the database
        List<Medida> medidaList = medidaRepository.findAll();
        assertThat(medidaList).hasSize(databaseSizeBeforeCreate + 1);
        Medida testMedida = medidaList.get(medidaList.size() - 1);
        assertThat(testMedida.getContornoBusto()).isEqualTo(DEFAULT_CONTORNO_BUSTO);
        assertThat(testMedida.getAnchoPecho()).isEqualTo(DEFAULT_ANCHO_PECHO);
        assertThat(testMedida.getAltoBusto()).isEqualTo(DEFAULT_ALTO_BUSTO);
        assertThat(testMedida.getBajoBusto()).isEqualTo(DEFAULT_BAJO_BUSTO);
        assertThat(testMedida.getAlturaPinza()).isEqualTo(DEFAULT_ALTURA_PINZA);
        assertThat(testMedida.getSeparacionBusto()).isEqualTo(DEFAULT_SEPARACION_BUSTO);
        assertThat(testMedida.getTalleDeltantero()).isEqualTo(DEFAULT_TALLE_DELTANTERO);
        assertThat(testMedida.getTalleEspalda()).isEqualTo(DEFAULT_TALLE_ESPALDA);
        assertThat(testMedida.getLargoCorset()).isEqualTo(DEFAULT_LARGO_CORSET);
        assertThat(testMedida.getCostado()).isEqualTo(DEFAULT_COSTADO);
        assertThat(testMedida.getHombro()).isEqualTo(DEFAULT_HOMBRO);
        assertThat(testMedida.getAnchoHombro()).isEqualTo(DEFAULT_ANCHO_HOMBRO);
        assertThat(testMedida.getLargoManga()).isEqualTo(DEFAULT_LARGO_MANGA);
        assertThat(testMedida.getSisaDelantero()).isEqualTo(DEFAULT_SISA_DELANTERO);
        assertThat(testMedida.getSisaEspalda()).isEqualTo(DEFAULT_SISA_ESPALDA);
        assertThat(testMedida.getContornoCintura()).isEqualTo(DEFAULT_CONTORNO_CINTURA);
        assertThat(testMedida.getAnteCadera()).isEqualTo(DEFAULT_ANTE_CADERA);
        assertThat(testMedida.getContornoCadera()).isEqualTo(DEFAULT_CONTORNO_CADERA);
        assertThat(testMedida.getPosicionCadera()).isEqualTo(DEFAULT_POSICION_CADERA);
        assertThat(testMedida.getLargoFalda()).isEqualTo(DEFAULT_LARGO_FALDA);
        assertThat(testMedida.getTipoFalda()).isEqualTo(DEFAULT_TIPO_FALDA);
        assertThat(testMedida.getFechaMedida()).isEqualTo(DEFAULT_FECHA_MEDIDA);
        assertThat(testMedida.getAnchoEspalda()).isEqualTo(DEFAULT_ANCHO_ESPALDA);
        assertThat(testMedida.getAnchoManga()).isEqualTo(DEFAULT_ANCHO_MANGA);
        assertThat(testMedida.getTiroPantalon()).isEqualTo(DEFAULT_TIRO_PANTALON);
        assertThat(testMedida.getAnchoPinzaPantalon()).isEqualTo(DEFAULT_ANCHO_PINZA_PANTALON);
        assertThat(testMedida.getAnchoRodillaPantalon()).isEqualTo(DEFAULT_ANCHO_RODILLA_PANTALON);
        assertThat(testMedida.getBotaPantalon()).isEqualTo(DEFAULT_BOTA_PANTALON);
        assertThat(testMedida.getLargoPantalon()).isEqualTo(DEFAULT_LARGO_PANTALON);
    }

    @Test
    @Transactional
    public void createMedidaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medidaRepository.findAll().size();

        // Create the Medida with an existing ID
        medida.setId(1L);
        MedidaDTO medidaDTO = medidaMapper.toDto(medida);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedidaMockMvc.perform(post("/api/medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Medida> medidaList = medidaRepository.findAll();
        assertThat(medidaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFechaMedidaIsRequired() throws Exception {
        int databaseSizeBeforeTest = medidaRepository.findAll().size();
        // set the field null
        medida.setFechaMedida(null);

        // Create the Medida, which fails.
        MedidaDTO medidaDTO = medidaMapper.toDto(medida);

        restMedidaMockMvc.perform(post("/api/medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medidaDTO)))
            .andExpect(status().isBadRequest());

        List<Medida> medidaList = medidaRepository.findAll();
        assertThat(medidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMedidas() throws Exception {
        // Initialize the database
        medidaRepository.saveAndFlush(medida);

        // Get all the medidaList
        restMedidaMockMvc.perform(get("/api/medidas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medida.getId().intValue())))
            .andExpect(jsonPath("$.[*].contornoBusto").value(hasItem(DEFAULT_CONTORNO_BUSTO.doubleValue())))
            .andExpect(jsonPath("$.[*].anchoPecho").value(hasItem(DEFAULT_ANCHO_PECHO.doubleValue())))
            .andExpect(jsonPath("$.[*].altoBusto").value(hasItem(DEFAULT_ALTO_BUSTO.doubleValue())))
            .andExpect(jsonPath("$.[*].bajoBusto").value(hasItem(DEFAULT_BAJO_BUSTO.doubleValue())))
            .andExpect(jsonPath("$.[*].alturaPinza").value(hasItem(DEFAULT_ALTURA_PINZA.doubleValue())))
            .andExpect(jsonPath("$.[*].separacionBusto").value(hasItem(DEFAULT_SEPARACION_BUSTO.doubleValue())))
            .andExpect(jsonPath("$.[*].talleDeltantero").value(hasItem(DEFAULT_TALLE_DELTANTERO.doubleValue())))
            .andExpect(jsonPath("$.[*].talleEspalda").value(hasItem(DEFAULT_TALLE_ESPALDA.doubleValue())))
            .andExpect(jsonPath("$.[*].largoCorset").value(hasItem(DEFAULT_LARGO_CORSET.doubleValue())))
            .andExpect(jsonPath("$.[*].costado").value(hasItem(DEFAULT_COSTADO.doubleValue())))
            .andExpect(jsonPath("$.[*].hombro").value(hasItem(DEFAULT_HOMBRO.doubleValue())))
            .andExpect(jsonPath("$.[*].anchoHombro").value(hasItem(DEFAULT_ANCHO_HOMBRO.doubleValue())))
            .andExpect(jsonPath("$.[*].largoManga").value(hasItem(DEFAULT_LARGO_MANGA.doubleValue())))
            .andExpect(jsonPath("$.[*].sisaDelantero").value(hasItem(DEFAULT_SISA_DELANTERO.doubleValue())))
            .andExpect(jsonPath("$.[*].sisaEspalda").value(hasItem(DEFAULT_SISA_ESPALDA.doubleValue())))
            .andExpect(jsonPath("$.[*].contornoCintura").value(hasItem(DEFAULT_CONTORNO_CINTURA.doubleValue())))
            .andExpect(jsonPath("$.[*].anteCadera").value(hasItem(DEFAULT_ANTE_CADERA.doubleValue())))
            .andExpect(jsonPath("$.[*].contornoCadera").value(hasItem(DEFAULT_CONTORNO_CADERA.doubleValue())))
            .andExpect(jsonPath("$.[*].posicionCadera").value(hasItem(DEFAULT_POSICION_CADERA.doubleValue())))
            .andExpect(jsonPath("$.[*].largoFalda").value(hasItem(DEFAULT_LARGO_FALDA.doubleValue())))
            .andExpect(jsonPath("$.[*].tipoFalda").value(hasItem(DEFAULT_TIPO_FALDA.toString())))
            .andExpect(jsonPath("$.[*].fechaMedida").value(hasItem(DEFAULT_FECHA_MEDIDA.toString())))
            .andExpect(jsonPath("$.[*].anchoEspalda").value(hasItem(DEFAULT_ANCHO_ESPALDA.doubleValue())))
            .andExpect(jsonPath("$.[*].anchoManga").value(hasItem(DEFAULT_ANCHO_MANGA.doubleValue())))
            .andExpect(jsonPath("$.[*].tiroPantalon").value(hasItem(DEFAULT_TIRO_PANTALON.doubleValue())))
            .andExpect(jsonPath("$.[*].anchoPinzaPantalon").value(hasItem(DEFAULT_ANCHO_PINZA_PANTALON.doubleValue())))
            .andExpect(jsonPath("$.[*].anchoRodillaPantalon").value(hasItem(DEFAULT_ANCHO_RODILLA_PANTALON.doubleValue())))
            .andExpect(jsonPath("$.[*].botaPantalon").value(hasItem(DEFAULT_BOTA_PANTALON.doubleValue())))
            .andExpect(jsonPath("$.[*].largoPantalon").value(hasItem(DEFAULT_LARGO_PANTALON.doubleValue())));
    }

    @Test
    @Transactional
    public void getMedida() throws Exception {
        // Initialize the database
        medidaRepository.saveAndFlush(medida);

        // Get the medida
        restMedidaMockMvc.perform(get("/api/medidas/{id}", medida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medida.getId().intValue()))
            .andExpect(jsonPath("$.contornoBusto").value(DEFAULT_CONTORNO_BUSTO.doubleValue()))
            .andExpect(jsonPath("$.anchoPecho").value(DEFAULT_ANCHO_PECHO.doubleValue()))
            .andExpect(jsonPath("$.altoBusto").value(DEFAULT_ALTO_BUSTO.doubleValue()))
            .andExpect(jsonPath("$.bajoBusto").value(DEFAULT_BAJO_BUSTO.doubleValue()))
            .andExpect(jsonPath("$.alturaPinza").value(DEFAULT_ALTURA_PINZA.doubleValue()))
            .andExpect(jsonPath("$.separacionBusto").value(DEFAULT_SEPARACION_BUSTO.doubleValue()))
            .andExpect(jsonPath("$.talleDeltantero").value(DEFAULT_TALLE_DELTANTERO.doubleValue()))
            .andExpect(jsonPath("$.talleEspalda").value(DEFAULT_TALLE_ESPALDA.doubleValue()))
            .andExpect(jsonPath("$.largoCorset").value(DEFAULT_LARGO_CORSET.doubleValue()))
            .andExpect(jsonPath("$.costado").value(DEFAULT_COSTADO.doubleValue()))
            .andExpect(jsonPath("$.hombro").value(DEFAULT_HOMBRO.doubleValue()))
            .andExpect(jsonPath("$.anchoHombro").value(DEFAULT_ANCHO_HOMBRO.doubleValue()))
            .andExpect(jsonPath("$.largoManga").value(DEFAULT_LARGO_MANGA.doubleValue()))
            .andExpect(jsonPath("$.sisaDelantero").value(DEFAULT_SISA_DELANTERO.doubleValue()))
            .andExpect(jsonPath("$.sisaEspalda").value(DEFAULT_SISA_ESPALDA.doubleValue()))
            .andExpect(jsonPath("$.contornoCintura").value(DEFAULT_CONTORNO_CINTURA.doubleValue()))
            .andExpect(jsonPath("$.anteCadera").value(DEFAULT_ANTE_CADERA.doubleValue()))
            .andExpect(jsonPath("$.contornoCadera").value(DEFAULT_CONTORNO_CADERA.doubleValue()))
            .andExpect(jsonPath("$.posicionCadera").value(DEFAULT_POSICION_CADERA.doubleValue()))
            .andExpect(jsonPath("$.largoFalda").value(DEFAULT_LARGO_FALDA.doubleValue()))
            .andExpect(jsonPath("$.tipoFalda").value(DEFAULT_TIPO_FALDA.toString()))
            .andExpect(jsonPath("$.fechaMedida").value(DEFAULT_FECHA_MEDIDA.toString()))
            .andExpect(jsonPath("$.anchoEspalda").value(DEFAULT_ANCHO_ESPALDA.doubleValue()))
            .andExpect(jsonPath("$.anchoManga").value(DEFAULT_ANCHO_MANGA.doubleValue()))
            .andExpect(jsonPath("$.tiroPantalon").value(DEFAULT_TIRO_PANTALON.doubleValue()))
            .andExpect(jsonPath("$.anchoPinzaPantalon").value(DEFAULT_ANCHO_PINZA_PANTALON.doubleValue()))
            .andExpect(jsonPath("$.anchoRodillaPantalon").value(DEFAULT_ANCHO_RODILLA_PANTALON.doubleValue()))
            .andExpect(jsonPath("$.botaPantalon").value(DEFAULT_BOTA_PANTALON.doubleValue()))
            .andExpect(jsonPath("$.largoPantalon").value(DEFAULT_LARGO_PANTALON.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMedida() throws Exception {
        // Get the medida
        restMedidaMockMvc.perform(get("/api/medidas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedida() throws Exception {
        // Initialize the database
        medidaRepository.saveAndFlush(medida);
        int databaseSizeBeforeUpdate = medidaRepository.findAll().size();

        // Update the medida
        Medida updatedMedida = medidaRepository.findOne(medida.getId());
        updatedMedida
            .contornoBusto(UPDATED_CONTORNO_BUSTO)
            .anchoPecho(UPDATED_ANCHO_PECHO)
            .altoBusto(UPDATED_ALTO_BUSTO)
            .bajoBusto(UPDATED_BAJO_BUSTO)
            .alturaPinza(UPDATED_ALTURA_PINZA)
            .separacionBusto(UPDATED_SEPARACION_BUSTO)
            .talleDeltantero(UPDATED_TALLE_DELTANTERO)
            .talleEspalda(UPDATED_TALLE_ESPALDA)
            .largoCorset(UPDATED_LARGO_CORSET)
            .costado(UPDATED_COSTADO)
            .hombro(UPDATED_HOMBRO)
            .anchoHombro(UPDATED_ANCHO_HOMBRO)
            .largoManga(UPDATED_LARGO_MANGA)
            .sisaDelantero(UPDATED_SISA_DELANTERO)
            .sisaEspalda(UPDATED_SISA_ESPALDA)
            .contornoCintura(UPDATED_CONTORNO_CINTURA)
            .anteCadera(UPDATED_ANTE_CADERA)
            .contornoCadera(UPDATED_CONTORNO_CADERA)
            .posicionCadera(UPDATED_POSICION_CADERA)
            .largoFalda(UPDATED_LARGO_FALDA)
            .tipoFalda(UPDATED_TIPO_FALDA)
            .fechaMedida(UPDATED_FECHA_MEDIDA)
            .anchoEspalda(UPDATED_ANCHO_ESPALDA)
            .anchoManga(UPDATED_ANCHO_MANGA)
            .tiroPantalon(UPDATED_TIRO_PANTALON)
            .anchoPinzaPantalon(UPDATED_ANCHO_PINZA_PANTALON)
            .anchoRodillaPantalon(UPDATED_ANCHO_RODILLA_PANTALON)
            .botaPantalon(UPDATED_BOTA_PANTALON)
            .largoPantalon(UPDATED_LARGO_PANTALON);
        MedidaDTO medidaDTO = medidaMapper.toDto(updatedMedida);

        restMedidaMockMvc.perform(put("/api/medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medidaDTO)))
            .andExpect(status().isOk());

        // Validate the Medida in the database
        List<Medida> medidaList = medidaRepository.findAll();
        assertThat(medidaList).hasSize(databaseSizeBeforeUpdate);
        Medida testMedida = medidaList.get(medidaList.size() - 1);
        assertThat(testMedida.getContornoBusto()).isEqualTo(UPDATED_CONTORNO_BUSTO);
        assertThat(testMedida.getAnchoPecho()).isEqualTo(UPDATED_ANCHO_PECHO);
        assertThat(testMedida.getAltoBusto()).isEqualTo(UPDATED_ALTO_BUSTO);
        assertThat(testMedida.getBajoBusto()).isEqualTo(UPDATED_BAJO_BUSTO);
        assertThat(testMedida.getAlturaPinza()).isEqualTo(UPDATED_ALTURA_PINZA);
        assertThat(testMedida.getSeparacionBusto()).isEqualTo(UPDATED_SEPARACION_BUSTO);
        assertThat(testMedida.getTalleDeltantero()).isEqualTo(UPDATED_TALLE_DELTANTERO);
        assertThat(testMedida.getTalleEspalda()).isEqualTo(UPDATED_TALLE_ESPALDA);
        assertThat(testMedida.getLargoCorset()).isEqualTo(UPDATED_LARGO_CORSET);
        assertThat(testMedida.getCostado()).isEqualTo(UPDATED_COSTADO);
        assertThat(testMedida.getHombro()).isEqualTo(UPDATED_HOMBRO);
        assertThat(testMedida.getAnchoHombro()).isEqualTo(UPDATED_ANCHO_HOMBRO);
        assertThat(testMedida.getLargoManga()).isEqualTo(UPDATED_LARGO_MANGA);
        assertThat(testMedida.getSisaDelantero()).isEqualTo(UPDATED_SISA_DELANTERO);
        assertThat(testMedida.getSisaEspalda()).isEqualTo(UPDATED_SISA_ESPALDA);
        assertThat(testMedida.getContornoCintura()).isEqualTo(UPDATED_CONTORNO_CINTURA);
        assertThat(testMedida.getAnteCadera()).isEqualTo(UPDATED_ANTE_CADERA);
        assertThat(testMedida.getContornoCadera()).isEqualTo(UPDATED_CONTORNO_CADERA);
        assertThat(testMedida.getPosicionCadera()).isEqualTo(UPDATED_POSICION_CADERA);
        assertThat(testMedida.getLargoFalda()).isEqualTo(UPDATED_LARGO_FALDA);
        assertThat(testMedida.getTipoFalda()).isEqualTo(UPDATED_TIPO_FALDA);
        assertThat(testMedida.getFechaMedida()).isEqualTo(UPDATED_FECHA_MEDIDA);
        assertThat(testMedida.getAnchoEspalda()).isEqualTo(UPDATED_ANCHO_ESPALDA);
        assertThat(testMedida.getAnchoManga()).isEqualTo(UPDATED_ANCHO_MANGA);
        assertThat(testMedida.getTiroPantalon()).isEqualTo(UPDATED_TIRO_PANTALON);
        assertThat(testMedida.getAnchoPinzaPantalon()).isEqualTo(UPDATED_ANCHO_PINZA_PANTALON);
        assertThat(testMedida.getAnchoRodillaPantalon()).isEqualTo(UPDATED_ANCHO_RODILLA_PANTALON);
        assertThat(testMedida.getBotaPantalon()).isEqualTo(UPDATED_BOTA_PANTALON);
        assertThat(testMedida.getLargoPantalon()).isEqualTo(UPDATED_LARGO_PANTALON);
    }

    @Test
    @Transactional
    public void updateNonExistingMedida() throws Exception {
        int databaseSizeBeforeUpdate = medidaRepository.findAll().size();

        // Create the Medida
        MedidaDTO medidaDTO = medidaMapper.toDto(medida);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMedidaMockMvc.perform(put("/api/medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medidaDTO)))
            .andExpect(status().isCreated());

        // Validate the Medida in the database
        List<Medida> medidaList = medidaRepository.findAll();
        assertThat(medidaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMedida() throws Exception {
        // Initialize the database
        medidaRepository.saveAndFlush(medida);
        int databaseSizeBeforeDelete = medidaRepository.findAll().size();

        // Get the medida
        restMedidaMockMvc.perform(delete("/api/medidas/{id}", medida.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Medida> medidaList = medidaRepository.findAll();
        assertThat(medidaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medida.class);
        Medida medida1 = new Medida();
        medida1.setId(1L);
        Medida medida2 = new Medida();
        medida2.setId(medida1.getId());
        assertThat(medida1).isEqualTo(medida2);
        medida2.setId(2L);
        assertThat(medida1).isNotEqualTo(medida2);
        medida1.setId(null);
        assertThat(medida1).isNotEqualTo(medida2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedidaDTO.class);
        MedidaDTO medidaDTO1 = new MedidaDTO();
        medidaDTO1.setId(1L);
        MedidaDTO medidaDTO2 = new MedidaDTO();
        assertThat(medidaDTO1).isNotEqualTo(medidaDTO2);
        medidaDTO2.setId(medidaDTO1.getId());
        assertThat(medidaDTO1).isEqualTo(medidaDTO2);
        medidaDTO2.setId(2L);
        assertThat(medidaDTO1).isNotEqualTo(medidaDTO2);
        medidaDTO1.setId(null);
        assertThat(medidaDTO1).isNotEqualTo(medidaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(medidaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(medidaMapper.fromId(null)).isNull();
    }
}
