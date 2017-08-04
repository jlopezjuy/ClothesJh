package com.anelsoftware.repository.search;

import com.anelsoftware.domain.FacturaPresupuesto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FacturaPresupuesto entity.
 */
public interface FacturaPresupuestoSearchRepository extends ElasticsearchRepository<FacturaPresupuesto, Long> {
}
