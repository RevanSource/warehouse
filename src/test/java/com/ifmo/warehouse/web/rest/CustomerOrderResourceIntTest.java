package com.ifmo.warehouse.web.rest;

import com.ifmo.warehouse.Application;
import com.ifmo.warehouse.domain.CustomerOrder;
import com.ifmo.warehouse.repository.CustomerOrderRepository;
import com.ifmo.warehouse.repository.search.CustomerOrderSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ifmo.warehouse.domain.enumeration.StatusType;

/**
 * Test class for the CustomerOrderResource REST controller.
 *
 * @see CustomerOrderResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CustomerOrderResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_ORDER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_ORDER_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_ORDER_DATE_STR = dateTimeFormatter.format(DEFAULT_ORDER_DATE);

    private static final ZonedDateTime DEFAULT_PLANNED_DELIVERY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_PLANNED_DELIVERY_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_PLANNED_DELIVERY_DATE_STR = dateTimeFormatter.format(DEFAULT_PLANNED_DELIVERY_DATE);

    private static final ZonedDateTime DEFAULT_ACTUAL_DELIVERY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_ACTUAL_DELIVERY_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_ACTUAL_DELIVERY_DATE_STR = dateTimeFormatter.format(DEFAULT_ACTUAL_DELIVERY_DATE);
    
    private static final StatusType DEFAULT_STATUS = StatusType.Pending;
    private static final StatusType UPDATED_STATUS = StatusType.InProgress;
    private static final String DEFAULT_OTHER_DETAILS = "AAAAA";
    private static final String UPDATED_OTHER_DETAILS = "BBBBB";

    @Inject
    private CustomerOrderRepository customerOrderRepository;

    @Inject
    private CustomerOrderSearchRepository customerOrderSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCustomerOrderMockMvc;

    private CustomerOrder customerOrder;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerOrderResource customerOrderResource = new CustomerOrderResource();
        ReflectionTestUtils.setField(customerOrderResource, "customerOrderSearchRepository", customerOrderSearchRepository);
        ReflectionTestUtils.setField(customerOrderResource, "customerOrderRepository", customerOrderRepository);
        this.restCustomerOrderMockMvc = MockMvcBuilders.standaloneSetup(customerOrderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        customerOrder = new CustomerOrder();
        customerOrder.setOrderDate(DEFAULT_ORDER_DATE);
        customerOrder.setPlannedDeliveryDate(DEFAULT_PLANNED_DELIVERY_DATE);
        customerOrder.setActualDeliveryDate(DEFAULT_ACTUAL_DELIVERY_DATE);
        customerOrder.setStatus(DEFAULT_STATUS);
        customerOrder.setOtherDetails(DEFAULT_OTHER_DETAILS);
    }

    @Test
    @Transactional
    public void createCustomerOrder() throws Exception {
        int databaseSizeBeforeCreate = customerOrderRepository.findAll().size();

        // Create the CustomerOrder

        restCustomerOrderMockMvc.perform(post("/api/customerOrders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customerOrder)))
                .andExpect(status().isCreated());

        // Validate the CustomerOrder in the database
        List<CustomerOrder> customerOrders = customerOrderRepository.findAll();
        assertThat(customerOrders).hasSize(databaseSizeBeforeCreate + 1);
        CustomerOrder testCustomerOrder = customerOrders.get(customerOrders.size() - 1);
        assertThat(testCustomerOrder.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testCustomerOrder.getPlannedDeliveryDate()).isEqualTo(DEFAULT_PLANNED_DELIVERY_DATE);
        assertThat(testCustomerOrder.getActualDeliveryDate()).isEqualTo(DEFAULT_ACTUAL_DELIVERY_DATE);
        assertThat(testCustomerOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCustomerOrder.getOtherDetails()).isEqualTo(DEFAULT_OTHER_DETAILS);
    }

    @Test
    @Transactional
    public void checkOrderDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerOrderRepository.findAll().size();
        // set the field null
        customerOrder.setOrderDate(null);

        // Create the CustomerOrder, which fails.

        restCustomerOrderMockMvc.perform(post("/api/customerOrders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customerOrder)))
                .andExpect(status().isBadRequest());

        List<CustomerOrder> customerOrders = customerOrderRepository.findAll();
        assertThat(customerOrders).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPlannedDeliveryDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerOrderRepository.findAll().size();
        // set the field null
        customerOrder.setPlannedDeliveryDate(null);

        // Create the CustomerOrder, which fails.

        restCustomerOrderMockMvc.perform(post("/api/customerOrders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customerOrder)))
                .andExpect(status().isBadRequest());

        List<CustomerOrder> customerOrders = customerOrderRepository.findAll();
        assertThat(customerOrders).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerOrderRepository.findAll().size();
        // set the field null
        customerOrder.setStatus(null);

        // Create the CustomerOrder, which fails.

        restCustomerOrderMockMvc.perform(post("/api/customerOrders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customerOrder)))
                .andExpect(status().isBadRequest());

        List<CustomerOrder> customerOrders = customerOrderRepository.findAll();
        assertThat(customerOrders).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerOrders() throws Exception {
        // Initialize the database
        customerOrderRepository.saveAndFlush(customerOrder);

        // Get all the customerOrders
        restCustomerOrderMockMvc.perform(get("/api/customerOrders?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(customerOrder.getId().intValue())))
                .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE_STR)))
                .andExpect(jsonPath("$.[*].plannedDeliveryDate").value(hasItem(DEFAULT_PLANNED_DELIVERY_DATE_STR)))
                .andExpect(jsonPath("$.[*].actualDeliveryDate").value(hasItem(DEFAULT_ACTUAL_DELIVERY_DATE_STR)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].otherDetails").value(hasItem(DEFAULT_OTHER_DETAILS.toString())));
    }

    @Test
    @Transactional
    public void getCustomerOrder() throws Exception {
        // Initialize the database
        customerOrderRepository.saveAndFlush(customerOrder);

        // Get the customerOrder
        restCustomerOrderMockMvc.perform(get("/api/customerOrders/{id}", customerOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(customerOrder.getId().intValue()))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE_STR))
            .andExpect(jsonPath("$.plannedDeliveryDate").value(DEFAULT_PLANNED_DELIVERY_DATE_STR))
            .andExpect(jsonPath("$.actualDeliveryDate").value(DEFAULT_ACTUAL_DELIVERY_DATE_STR))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.otherDetails").value(DEFAULT_OTHER_DETAILS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerOrder() throws Exception {
        // Get the customerOrder
        restCustomerOrderMockMvc.perform(get("/api/customerOrders/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerOrder() throws Exception {
        // Initialize the database
        customerOrderRepository.saveAndFlush(customerOrder);

		int databaseSizeBeforeUpdate = customerOrderRepository.findAll().size();

        // Update the customerOrder
        customerOrder.setOrderDate(UPDATED_ORDER_DATE);
        customerOrder.setPlannedDeliveryDate(UPDATED_PLANNED_DELIVERY_DATE);
        customerOrder.setActualDeliveryDate(UPDATED_ACTUAL_DELIVERY_DATE);
        customerOrder.setStatus(UPDATED_STATUS);
        customerOrder.setOtherDetails(UPDATED_OTHER_DETAILS);

        restCustomerOrderMockMvc.perform(put("/api/customerOrders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customerOrder)))
                .andExpect(status().isOk());

        // Validate the CustomerOrder in the database
        List<CustomerOrder> customerOrders = customerOrderRepository.findAll();
        assertThat(customerOrders).hasSize(databaseSizeBeforeUpdate);
        CustomerOrder testCustomerOrder = customerOrders.get(customerOrders.size() - 1);
        assertThat(testCustomerOrder.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testCustomerOrder.getPlannedDeliveryDate()).isEqualTo(UPDATED_PLANNED_DELIVERY_DATE);
        assertThat(testCustomerOrder.getActualDeliveryDate()).isEqualTo(UPDATED_ACTUAL_DELIVERY_DATE);
        assertThat(testCustomerOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustomerOrder.getOtherDetails()).isEqualTo(UPDATED_OTHER_DETAILS);
    }

    @Test
    @Transactional
    public void deleteCustomerOrder() throws Exception {
        // Initialize the database
        customerOrderRepository.saveAndFlush(customerOrder);

		int databaseSizeBeforeDelete = customerOrderRepository.findAll().size();

        // Get the customerOrder
        restCustomerOrderMockMvc.perform(delete("/api/customerOrders/{id}", customerOrder.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomerOrder> customerOrders = customerOrderRepository.findAll();
        assertThat(customerOrders).hasSize(databaseSizeBeforeDelete - 1);
    }
}
