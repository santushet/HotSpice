package com.spice.hot.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.spice.hot.domain.Category;
import com.spice.hot.domain.Dish;
import com.spice.hot.repository.CategoryRepository;
import com.spice.hot.repository.DishRepository;

/**
 * Service Implementation for managing Dish.
 */
@Service
public class DishService {

    private final Logger log = LoggerFactory.getLogger(DishService.class);
    
    @Inject
    private DishRepository dishRepository;
    
    @Inject
    private CategoryRepository catRepository;
    
    /**
     * Save a dish.
     * @return the persisted entity
     */
    public Dish save(Dish dish) {
        log.debug("Request to save Dish : {}", dish);
        Category cat=catRepository.findByName(dish.getCategory().getName());
        dish.setCategory(cat);
        Dish result = dishRepository.save(dish);
        return result;
    }

    /**
     *  get all the dishs.
     *  @return the list of entities
     */
    public List<Dish> findAll() {
        log.debug("Request to get all Dishs");
        List<Dish> result = dishRepository.findAll();
        return result;
    }
    
    /**
     *  get all the dishs.
     *  @return the list of dishes based on category
     */
    public List<Dish> findBySlug(String slug) {
        log.debug("Request to get all Dishs by slug");
        List<Dish> result = dishRepository.findDishBySlug(slug);
        return result;
    }
    

    /**
     *  get one dish by id.
     *  @return the entity
     */
    public Dish findOne(String id) {
        log.debug("Request to get Dish : {}", id);
        Dish dish = dishRepository.findOne(id);
        return dish;
    }

    /**
     *  delete the  dish by id.
     */
    public void delete(String id) {
        log.debug("Request to delete Dish : {}", id);
        dishRepository.delete(id);
    }
}
