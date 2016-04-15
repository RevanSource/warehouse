package com.ifmo.warehouse.repository;

import com.ifmo.warehouse.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Store entity.
 */
public interface StoreRepository extends JpaRepository<Store,Long> {

}
