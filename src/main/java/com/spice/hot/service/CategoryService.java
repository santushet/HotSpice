package com.spice.hot.service;

import java.util.regex.Pattern;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spice.hot.domain.Category;
import com.spice.hot.repository.CategoryRepository;

/**
 * Service Implementation for managing Category.
 */
@Service
public class CategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryService.class);
	private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    
    @Inject
    private CategoryRepository categoryRepository;
    
    /**
     * Save a category.
     * @return the persisted entity
     */
    public Category save(Category category) {
		log.debug("Before slugify-Request to save Category : {}", category);

		String nowhitespace = WHITESPACE.matcher(category.getName())
				.replaceAll("-");
		category.setSlug(nowhitespace.toLowerCase().replaceAll(
				"[^a-z0-9-]", ""));
		log.debug("After slugify-Request to save Category : {}", category);
		Category result = categoryRepository.save(category);
		return result;
	}


    /**
     *  get all the categorys.
     *  @return the list of entities
     */
    public Page<Category> findAll(Pageable pageable) {
        log.debug("Request to get all Categorys");
        Page<Category> result = categoryRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one category by id.
     *  @return the entity
     */
    public Category findOne(String id) {
        log.debug("Request to get Category : {}", id);
        Category category = categoryRepository.findOne(id);
        return category;
    }

    /**
     *  delete the  category by id.
     */
    public void delete(String id) {
        log.debug("Request to delete Category : {}", id);
        categoryRepository.delete(id);
    }
}
