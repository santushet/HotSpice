package com.spice.hot.repository;

import com.spice.hot.domain.Category;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Category entity.
 */
public interface CategoryRepository extends MongoRepository<Category,String> {
	
	public Category findByName(String name);

}