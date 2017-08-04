package com.anelsoftware.repository.search;

import com.anelsoftware.domain.Rubro;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Rubro entity.
 */
public interface RubroSearchRepository extends ElasticsearchRepository<Rubro, Long> {
}
