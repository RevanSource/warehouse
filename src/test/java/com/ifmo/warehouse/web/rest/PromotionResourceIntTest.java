package com.ifmo.warehouse.web.rest;

import com.ifmo.warehouse.Application;
import com.ifmo.warehouse.domain.Promotion;
import com.ifmo.warehouse.repository.PromotionRepository;
import com.ifmo.warehouse.repository.search.PromotionSearchRepository;

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


/**
 * Test class for the PromotionResource REST controller.
 *
 * @see PromotionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PromotionResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final ZonedDateTime DEFAULT_DATE_FROM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE_FROM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_FROM_STR = dateTimeFormatter.format(DEFAULT_DATE_FROM);

    private static final ZonedDateTime DEFAULT_DATE_TO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE_TO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_TO_STR = dateTimeFormatter.format(DEFAULT_DATE_TO);
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_OTHER_DETAILS = "AAAAA";
    private static final String UPDATED_OTHER_DETAILS = "BBBBB";

    @Inject
    private PromotionRepository promotionRepository;

    @Inject
    private PromotionSearchRepository promotionSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPromotionMockMvc;

    private Promotion promotion;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PromotionResource promotionResource = new PromotionResource();
        ReflectionTestUtils.setField(promotionResource, "promotionSearchRepository", promotionSearchRepository);
        ReflectionTestUtils.setField(promotionResource, "promotionRepository", promotionRepository);
        this.restPromotionMockMvc = MockMvcBuilders.standaloneSetup(promotionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        promotion = new Promotion();
        promotion.setName(DEFAULT_NAME);
        promotion.setDateFrom(DEFAULT_DATE_FROM);
        promotion.setDateTo(DEFAULT_DATE_TO);
        promotion.setDescription(DEFAULT_DESCRIPTION);
        promotion.setOtherDetails(DEFAULT_OTHER_DETAILS);
    }

    @Test
    @Transactional
    public void createPromotion() throws Exception {
        int databaseSizeBeforeCreate = promotionRepository.findAll().size();

        // Create the Promotion

        restPromotionMockMvc.perform(post("/api/promotions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(promotion)))
                .andExpect(status().isCreated());

        // Validate the Promotion in the database
        List<Promotion> promotions = promotionRepository.findAll();
        assertThat(promotions).hasSize(databaseSizeBeforeCreate + 1);
        Promotion testPromotion = promotions.get(promotions.size() - 1);
        assertThat(testPromotion.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPromotion.getDateFrom()).isEqualTo(DEFAULT_DATE_FROM);
        assertThat(testPromotion.getDateTo()).isEqualTo(DEFAULT_DATE_TO);
        assertThat(testPromotion.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPromotion.getOtherDetails()).isEqualTo(DEFAULT_OTHER_DETAILS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = promotionRepository.findAll().size();
        // set the field null
        promotion.setName(null);

        // Create the Promotion, which fails.

        restPromotionMockMvc.perform(post("/api/promotions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(promotion)))
                .andExpect(status().isBadRequest());

        List<Promotion> promotions = promotionRepository.findAll();
        assertThat(promotions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = promotionRepository.findAll().size();
        // set the field null
        promotion.setDateFrom(null);

        // Create the Promotion, which fails.

        restPromotionMockMvc.perform(post("/api/promotions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(promotion)))
                .andExpect(status().isBadRequest());

        List<Promotion> promotions = promotionRepository.findAll();
        assertThat(promotions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateToIsRequired() throws Exception {
        int databaseSizeBeforeTest = promotionRepository.findAll().size();
        // set the field null
        promotion.setDateTo(null);

        // Create the Promotion, which fails.

        restPromotionMockMvc.perform(post("/api/promotions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(promotion)))
                .andExpect(status().isBadRequest());

        List<Promotion> promotions = promotionRepository.findAll();
        assertThat(promotions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPromotions() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotions
        restPromotionMockMvc.perform(get("/api/promotions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(promotion.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].dateFrom").value(hasItem(DEFAULT_DATE_FROM_STR)))
                .andExpect(jsonPath("$.[*].dateTo").value(hasItem(DEFAULT_DATE_TO_STR)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].otherDetails").value(hasItem(DEFAULT_OTHER_DETAILS.toString())));
    }

    @Test
    @Transactional
    public void getPromotion() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get the promotion
        restPromotionMockMvc.perform(get("/api/promotions/{id}", promotion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(promotion.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.dateFrom").value(DEFAULT_DATE_FROM_STR))
            .andExpect(jsonPath("$.dateTo").value(DEFAULT_DATE_TO_STR))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.otherDetails").value(DEFAULT_OTHER_DETAILS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPromotion() throws Exception {
        // Get the promotion
        restPromotionMockMvc.perform(get("/api/promotions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePromotion() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

		int databaseSizeBeforeUpdate = promotionRepository.findAll().size();

        // Update the promotion
        promotion.setName(UPDATED_NAME);
        promotion.setDateFrom(UPDATED_DATE_FROM);
        promotion.setDateTo(UPDATED_DATE_TO);
        promotion.setDescription(UPDATED_DESCRIPTION);
        promotion.setOtherDetails(UPDATED_OTHER_DETAILS);

        restPromotionMockMvc.perform(put("/api/promotions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(promotion)))
                .andExpect(status().isOk());

        // Validate the Promotion in the database
        List<Promotion> promotions = promotionRepository.findAll();
        assertThat(promotions).hasSize(databaseSizeBeforeUpdate);
        Promotion testPromotion = promotions.get(promotions.size() - 1);
        assertThat(testPromotion.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPromotion.getDateFrom()).isEqualTo(UPDATED_DATE_FROM);
        assertThat(testPromotion.getDateTo()).isEqualTo(UPDATED_DATE_TO);
        assertThat(testPromotion.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPromotion.getOtherDetails()).isEqualTo(UPDATED_OTHER_DETAILS);
    }

    @Test
    @Transactional
    public void deletePromotion() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

		int databaseSizeBeforeDelete = promotionRepository.findAll().size();

        // Get the promotion
        restPromotionMockMvc.perform(delete("/api/promotions/{id}", promotion.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Promotion> promotions = promotionRepository.findAll();
        assertThat(promotions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
