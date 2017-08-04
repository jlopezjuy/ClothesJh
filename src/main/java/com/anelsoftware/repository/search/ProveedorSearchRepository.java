package com.anelsoftware.repository.search;

import com.anelsoftware.domain.Proveedor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Proveedor entity.
 */
public interface ProveedorSearchRepository extends ElasticsearchRepository<Proveedor, Long> {
}
