package com.anelsoftware.repository.search;

import com.anelsoftware.domain.DetalleFactPres;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DetalleFactPres entity.
 */
public interface DetalleFactPresSearchRepository extends ElasticsearchRepository<DetalleFactPres, Long> {
}
