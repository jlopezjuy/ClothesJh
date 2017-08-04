package com.anelsoftware.repository.search;

import com.anelsoftware.domain.Encargo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Encargo entity.
 */
public interface EncargoSearchRepository extends ElasticsearchRepository<Encargo, Long> {
}
