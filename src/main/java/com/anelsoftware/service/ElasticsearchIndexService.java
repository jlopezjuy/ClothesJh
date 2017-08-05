package com.anelsoftware.service;

import com.codahale.metrics.annotation.Timed;
import com.anelsoftware.domain.*;
import com.anelsoftware.repository.*;
import com.anelsoftware.repository.search.*;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

@Service
public class ElasticsearchIndexService {

    private final Logger log = LoggerFactory.getLogger(ElasticsearchIndexService.class);

    private final ClienteRepository clienteRepository;

    private final ClienteSearchRepository clienteSearchRepository;

    private final DetalleFactPresRepository detalleFactPresRepository;

    private final DetalleFactPresSearchRepository detalleFactPresSearchRepository;

    private final EncargoRepository encargoRepository;

    private final EncargoSearchRepository encargoSearchRepository;

    private final FacturaPresupuestoRepository facturaPresupuestoRepository;

    private final FacturaPresupuestoSearchRepository facturaPresupuestoSearchRepository;

    private final MedidaRepository medidaRepository;

    private final MedidaSearchRepository medidaSearchRepository;

    private final ModeloRepository modeloRepository;

    private final ModeloSearchRepository modeloSearchRepository;

    private final PagoRepository pagoRepository;

    private final PagoSearchRepository pagoSearchRepository;

    private final ProductoRepository productoRepository;

    private final ProductoSearchRepository productoSearchRepository;

    private final ProveedorRepository proveedorRepository;

    private final ProveedorSearchRepository proveedorSearchRepository;

    private final RubroRepository rubroRepository;

    private final RubroSearchRepository rubroSearchRepository;

    private final UserRepository userRepository;

    private final UserSearchRepository userSearchRepository;

    private final ElasticsearchTemplate elasticsearchTemplate;

    public ElasticsearchIndexService(
        UserRepository userRepository,
        UserSearchRepository userSearchRepository,
        ClienteRepository clienteRepository,
        ClienteSearchRepository clienteSearchRepository,
        DetalleFactPresRepository detalleFactPresRepository,
        DetalleFactPresSearchRepository detalleFactPresSearchRepository,
        EncargoRepository encargoRepository,
        EncargoSearchRepository encargoSearchRepository,
        FacturaPresupuestoRepository facturaPresupuestoRepository,
        FacturaPresupuestoSearchRepository facturaPresupuestoSearchRepository,
        MedidaRepository medidaRepository,
        MedidaSearchRepository medidaSearchRepository,
        ModeloRepository modeloRepository,
        ModeloSearchRepository modeloSearchRepository,
        PagoRepository pagoRepository,
        PagoSearchRepository pagoSearchRepository,
        ProductoRepository productoRepository,
        ProductoSearchRepository productoSearchRepository,
        ProveedorRepository proveedorRepository,
        ProveedorSearchRepository proveedorSearchRepository,
        RubroRepository rubroRepository,
        RubroSearchRepository rubroSearchRepository,
        ElasticsearchTemplate elasticsearchTemplate) {
        this.userRepository = userRepository;
        this.userSearchRepository = userSearchRepository;
        this.clienteRepository = clienteRepository;
        this.clienteSearchRepository = clienteSearchRepository;
        this.detalleFactPresRepository = detalleFactPresRepository;
        this.detalleFactPresSearchRepository = detalleFactPresSearchRepository;
        this.encargoRepository = encargoRepository;
        this.encargoSearchRepository = encargoSearchRepository;
        this.facturaPresupuestoRepository = facturaPresupuestoRepository;
        this.facturaPresupuestoSearchRepository = facturaPresupuestoSearchRepository;
        this.medidaRepository = medidaRepository;
        this.medidaSearchRepository = medidaSearchRepository;
        this.modeloRepository = modeloRepository;
        this.modeloSearchRepository = modeloSearchRepository;
        this.pagoRepository = pagoRepository;
        this.pagoSearchRepository = pagoSearchRepository;
        this.productoRepository = productoRepository;
        this.productoSearchRepository = productoSearchRepository;
        this.proveedorRepository = proveedorRepository;
        this.proveedorSearchRepository = proveedorSearchRepository;
        this.rubroRepository = rubroRepository;
        this.rubroSearchRepository = rubroSearchRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Async
    @Timed
    public void reindexAll() {
        reindexForClass(Cliente.class, clienteRepository, clienteSearchRepository);
        reindexForClass(DetalleFactPres.class, detalleFactPresRepository, detalleFactPresSearchRepository);
        reindexForClass(Encargo.class, encargoRepository, encargoSearchRepository);
        reindexForClass(FacturaPresupuesto.class, facturaPresupuestoRepository, facturaPresupuestoSearchRepository);
        reindexForClass(Medida.class, medidaRepository, medidaSearchRepository);
        reindexForClass(Modelo.class, modeloRepository, modeloSearchRepository);
        reindexForClass(Pago.class, pagoRepository, pagoSearchRepository);
        reindexForClass(Producto.class, productoRepository, productoSearchRepository);
        reindexForClass(Proveedor.class, proveedorRepository, proveedorSearchRepository);
        reindexForClass(Rubro.class, rubroRepository, rubroSearchRepository);
        reindexForClass(User.class, userRepository, userSearchRepository);

        log.info("Elasticsearch: Successfully performed reindexing");
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    private <T, ID extends Serializable> void reindexForClass(Class<T> entityClass, JpaRepository<T, ID> jpaRepository,
                                                              ElasticsearchRepository<T, ID> elasticsearchRepository) {
        elasticsearchTemplate.deleteIndex(entityClass);
        try {
            elasticsearchTemplate.createIndex(entityClass);
        } catch (IndexAlreadyExistsException e) {
            // Do nothing. Index was already concurrently recreated by some other service.
        }
        elasticsearchTemplate.putMapping(entityClass);
        if (jpaRepository.count() > 0) {
            try {
                Method m = jpaRepository.getClass().getMethod("findAllWithEagerRelationships");
                elasticsearchRepository.save((List<T>) m.invoke(jpaRepository));
            } catch (Exception e) {
                elasticsearchRepository.save(jpaRepository.findAll());
            }
        }
        log.info("Elasticsearch: Indexed all rows for " + entityClass.getSimpleName());
    }
}
