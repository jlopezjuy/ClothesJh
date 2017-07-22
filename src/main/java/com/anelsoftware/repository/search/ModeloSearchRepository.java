package com.anelsoftware.repository.search;

import com.anelsoftware.domain.Modelo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Modelo entity.
 */
public interface ModeloSearchRepository extends ElasticsearchRepository<Modelo, Long> {
}
