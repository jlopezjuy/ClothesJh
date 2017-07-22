package com.anelsoftware.repository.search;

import com.anelsoftware.domain.Medida;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Medida entity.
 */
public interface MedidaSearchRepository extends ElasticsearchRepository<Medida, Long> {
}
