package com.anelsoftware.web.rest;

import com.anelsoftware.ClothesApp;

import com.anelsoftware.domain.Cliente;
import com.anelsoftware.repository.ClienteRepository;
import com.anelsoftware.service.dto.ClienteDTO;
import com.anelsoftware.service.mapper.ClienteMapper;
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
 * Test class for the ClienteResource REST controller.
 *
 * @see ClienteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClothesApp.class)
public class ClienteResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_CELULAR = "AAAAAAAAAA";
    private static final String UPDATED_CELULAR = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_DOMICILIO = "AAAAAAAAAA";
    private static final String UPDATED_DOMICILIO = "BBBBBBBBBB";

    private static final String DEFAULT_COLEGIO = "AAAAAAAAAA";
    private static final String UPDATED_COLEGIO = "BBBBBBBBBB";

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClienteMockMvc;

    private Cliente cliente;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClienteResource clienteResource = new ClienteResource(clienteRepository, clienteMapper);
        this.restClienteMockMvc = MockMvcBuilders.standaloneSetup(clienteResource)
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
    public static Cliente createEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .nombre(DEFAULT_NOMBRE)
            .apellido(DEFAULT_APELLIDO)
            .celular(DEFAULT_CELULAR)
            .telefono(DEFAULT_TELEFONO)
            .email(DEFAULT_EMAIL)
            .domicilio(DEFAULT_DOMICILIO)
            .colegio(DEFAULT_COLEGIO);
        return cliente;
    }

    @Before
    public void initTest() {
        cliente = createEntity(em);
    }

    @Test
    @Transactional
    public void createCliente() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);
        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate + 1);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCliente.getApellido()).isEqualTo(DEFAULT_APELLIDO);
        assertThat(testCliente.getCelular()).isEqualTo(DEFAULT_CELULAR);
        assertThat(testCliente.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testCliente.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCliente.getDomicilio()).isEqualTo(DEFAULT_DOMICILIO);
        assertThat(testCliente.getColegio()).isEqualTo(DEFAULT_COLEGIO);
    }

    @Test
    @Transactional
    public void createClienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // Create the Cliente with an existing ID
        cliente.setId(1L);
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setNombre(null);

        // Create the Cliente, which fails.
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setApellido(null);

        // Create the Cliente, which fails.
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setTelefono(null);

        // Create the Cliente, which fails.
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setEmail(null);

        // Create the Cliente, which fails.
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDomicilioIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setDomicilio(null);

        // Create the Cliente, which fails.
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkColegioIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setColegio(null);

        // Create the Cliente, which fails.
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClientes() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList
        restClienteMockMvc.perform(get("/api/clientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO.toString())))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].domicilio").value(hasItem(DEFAULT_DOMICILIO.toString())))
            .andExpect(jsonPath("$.[*].colegio").value(hasItem(DEFAULT_COLEGIO.toString())));
    }

    @Test
    @Transactional
    public void getCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", cliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cliente.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO.toString()))
            .andExpect(jsonPath("$.celular").value(DEFAULT_CELULAR.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.domicilio").value(DEFAULT_DOMICILIO.toString()))
            .andExpect(jsonPath("$.colegio").value(DEFAULT_COLEGIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCliente() throws Exception {
        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Update the cliente
        Cliente updatedCliente = clienteRepository.findOne(cliente.getId());
        updatedCliente
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .celular(UPDATED_CELULAR)
            .telefono(UPDATED_TELEFONO)
            .email(UPDATED_EMAIL)
            .domicilio(UPDATED_DOMICILIO)
            .colegio(UPDATED_COLEGIO);
        ClienteDTO clienteDTO = clienteMapper.toDto(updatedCliente);

        restClienteMockMvc.perform(put("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isOk());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCliente.getApellido()).isEqualTo(UPDATED_APELLIDO);
        assertThat(testCliente.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testCliente.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testCliente.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCliente.getDomicilio()).isEqualTo(UPDATED_DOMICILIO);
        assertThat(testCliente.getColegio()).isEqualTo(UPDATED_COLEGIO);
    }

    @Test
    @Transactional
    public void updateNonExistingCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClienteMockMvc.perform(put("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);
        int databaseSizeBeforeDelete = clienteRepository.findAll().size();

        // Get the cliente
        restClienteMockMvc.perform(delete("/api/clientes/{id}", cliente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cliente.class);
        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        Cliente cliente2 = new Cliente();
        cliente2.setId(cliente1.getId());
        assertThat(cliente1).isEqualTo(cliente2);
        cliente2.setId(2L);
        assertThat(cliente1).isNotEqualTo(cliente2);
        cliente1.setId(null);
        assertThat(cliente1).isNotEqualTo(cliente2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClienteDTO.class);
        ClienteDTO clienteDTO1 = new ClienteDTO();
        clienteDTO1.setId(1L);
        ClienteDTO clienteDTO2 = new ClienteDTO();
        assertThat(clienteDTO1).isNotEqualTo(clienteDTO2);
        clienteDTO2.setId(clienteDTO1.getId());
        assertThat(clienteDTO1).isEqualTo(clienteDTO2);
        clienteDTO2.setId(2L);
        assertThat(clienteDTO1).isNotEqualTo(clienteDTO2);
        clienteDTO1.setId(null);
        assertThat(clienteDTO1).isNotEqualTo(clienteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clienteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clienteMapper.fromId(null)).isNull();
    }
}
