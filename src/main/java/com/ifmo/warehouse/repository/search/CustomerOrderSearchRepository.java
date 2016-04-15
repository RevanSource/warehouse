package com.ifmo.warehouse.repository.search;

import com.ifmo.warehouse.domain.CustomerOrder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CustomerOrder entity.
 */
public interface CustomerOrderSearchRepository extends ElasticsearchRepository<CustomerOrder, Long> {
}
