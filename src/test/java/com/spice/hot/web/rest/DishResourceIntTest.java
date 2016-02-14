package com.spice.hot.web.rest;

import com.spice.hot.Application;
import com.spice.hot.domain.Dish;
import com.spice.hot.repository.DishRepository;
import com.spice.hot.service.DishService;

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

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the DishResource REST controller.
 *
 * @see DishResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DishResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final Integer DEFAULT_STOCK = 0;
    private static final Integer UPDATED_STOCK = 1;
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private DishRepository dishRepository;

    @Inject
    private DishService dishService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDishMockMvc;

    private Dish dish;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DishResource dishResource = new DishResource();
        ReflectionTestUtils.setField(dishResource, "dishService", dishService);
        this.restDishMockMvc = MockMvcBuilders.standaloneSetup(dishResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dishRepository.deleteAll();
        dish = new Dish();
        dish.setName(DEFAULT_NAME);
        dish.setPrice(DEFAULT_PRICE);
        dish.setStock(DEFAULT_STOCK);
        dish.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    public void createDish() throws Exception {
        int databaseSizeBeforeCreate = dishRepository.findAll().size();

        // Create the Dish

        restDishMockMvc.perform(post("/api/dishs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dish)))
                .andExpect(status().isCreated());

        // Validate the Dish in the database
        List<Dish> dishs = dishRepository.findAll();
        assertThat(dishs).hasSize(databaseSizeBeforeCreate + 1);
        Dish testDish = dishs.get(dishs.size() - 1);
        assertThat(testDish.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDish.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testDish.getStock()).isEqualTo(DEFAULT_STOCK);
        assertThat(testDish.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dishRepository.findAll().size();
        // set the field null
        dish.setName(null);

        // Create the Dish, which fails.

        restDishMockMvc.perform(post("/api/dishs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dish)))
                .andExpect(status().isBadRequest());

        List<Dish> dishs = dishRepository.findAll();
        assertThat(dishs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = dishRepository.findAll().size();
        // set the field null
        dish.setPrice(null);

        // Create the Dish, which fails.

        restDishMockMvc.perform(post("/api/dishs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dish)))
                .andExpect(status().isBadRequest());

        List<Dish> dishs = dishRepository.findAll();
        assertThat(dishs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllDishs() throws Exception {
        // Initialize the database
        dishRepository.save(dish);

        // Get all the dishs
        restDishMockMvc.perform(get("/api/dishs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dish.getId())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    public void getDish() throws Exception {
        // Initialize the database
        dishRepository.save(dish);

        // Get the dish
        restDishMockMvc.perform(get("/api/dishs/{id}", dish.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dish.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    public void getNonExistingDish() throws Exception {
        // Get the dish
        restDishMockMvc.perform(get("/api/dishs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateDish() throws Exception {
        // Initialize the database
        dishRepository.save(dish);

		int databaseSizeBeforeUpdate = dishRepository.findAll().size();

        // Update the dish
        dish.setName(UPDATED_NAME);
        dish.setPrice(UPDATED_PRICE);
        dish.setStock(UPDATED_STOCK);
        dish.setDescription(UPDATED_DESCRIPTION);

        restDishMockMvc.perform(put("/api/dishs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dish)))
                .andExpect(status().isOk());

        // Validate the Dish in the database
        List<Dish> dishs = dishRepository.findAll();
        assertThat(dishs).hasSize(databaseSizeBeforeUpdate);
        Dish testDish = dishs.get(dishs.size() - 1);
        assertThat(testDish.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDish.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testDish.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testDish.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    public void deleteDish() throws Exception {
        // Initialize the database
        dishRepository.save(dish);

		int databaseSizeBeforeDelete = dishRepository.findAll().size();

        // Get the dish
        restDishMockMvc.perform(delete("/api/dishs/{id}", dish.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Dish> dishs = dishRepository.findAll();
        assertThat(dishs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
