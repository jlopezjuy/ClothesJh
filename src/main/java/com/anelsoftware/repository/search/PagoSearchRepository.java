package com.anelsoftware.repository.search;

import com.anelsoftware.domain.Pago;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Pago entity.
 */
public interface PagoSearchRepository extends ElasticsearchRepository<Pago, Long> {
}
