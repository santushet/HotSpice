package com.spice.hot.repository;

import java.util.List;

import com.spice.hot.domain.Category;
import com.spice.hot.domain.Dish;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * Spring Data MongoDB repository for the Dish entity.
 */
public interface DishRepository extends MongoRepository<Dish,String> {

	@Query(value="{'category.slug': ?0}")
	public  List<Dish> findDishBySlug(String slug);
}
