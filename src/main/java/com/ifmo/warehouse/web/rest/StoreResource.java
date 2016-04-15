package com.ifmo.warehouse.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ifmo.warehouse.domain.Store;
import com.ifmo.warehouse.repository.StoreRepository;
import com.ifmo.warehouse.repository.search.StoreSearchRepository;
import com.ifmo.warehouse.web.rest.util.HeaderUtil;
import com.ifmo.warehouse.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Store.
 */
@RestController
@RequestMapping("/api")
public class StoreResource {

    private final Logger log = LoggerFactory.getLogger(StoreResource.class);
        
    @Inject
    private StoreRepository storeRepository;
    
    @Inject
    private StoreSearchRepository storeSearchRepository;
    
    /**
     * POST  /stores -> Create a new store.
     */
    @RequestMapping(value = "/stores",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Store> createStore(@Valid @RequestBody Store store) throws URISyntaxException {
        log.debug("REST request to save Store : {}", store);
        if (store.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("store", "idexists", "A new store cannot already have an ID")).body(null);
        }
        Store result = storeRepository.save(store);
        storeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/stores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("store", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stores -> Updates an existing store.
     */
    @RequestMapping(value = "/stores",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Store> updateStore(@Valid @RequestBody Store store) throws URISyntaxException {
        log.debug("REST request to update Store : {}", store);
        if (store.getId() == null) {
            return createStore(store);
        }
        Store result = storeRepository.save(store);
        storeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("store", store.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stores -> get all the stores.
     */
    @RequestMapping(value = "/stores",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Store>> getAllStores(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Stores");
        Page<Store> page = storeRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/stores");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /stores/:id -> get the "id" store.
     */
    @RequestMapping(value = "/stores/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Store> getStore(@PathVariable Long id) {
        log.debug("REST request to get Store : {}", id);
        Store store = storeRepository.findOne(id);
        return Optional.ofNullable(store)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /stores/:id -> delete the "id" store.
     */
    @RequestMapping(value = "/stores/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        log.debug("REST request to delete Store : {}", id);
        storeRepository.delete(id);
        storeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("store", id.toString())).build();
    }

    /**
     * SEARCH  /_search/stores/:query -> search for the store corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/stores/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Store> searchStores(@PathVariable String query) {
        log.debug("REST request to search Stores for query {}", query);
        return StreamSupport
            .stream(storeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
