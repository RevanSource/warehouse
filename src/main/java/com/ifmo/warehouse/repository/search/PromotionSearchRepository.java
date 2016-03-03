package com.ifmo.warehouse.repository.search;

import com.ifmo.warehouse.domain.Promotion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Promotion entity.
 */
public interface PromotionSearchRepository extends ElasticsearchRepository<Promotion, Long> {
}
