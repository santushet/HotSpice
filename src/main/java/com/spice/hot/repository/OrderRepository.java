package com.spice.hot.repository;

import java.util.List;

import com.spice.hot.domain.Dish;
import com.spice.hot.domain.Order;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * Spring Data MongoDB repository for the Order entity.
 */
public interface OrderRepository extends MongoRepository<Order,String> {

	@Query(value="{'user.login': ?0}")
	public  List<Order> findByUser(String login);
}
