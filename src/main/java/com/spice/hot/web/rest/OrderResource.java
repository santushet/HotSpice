package com.spice.hot.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spice.hot.domain.Order;
import com.spice.hot.security.AuthoritiesConstants;
import com.spice.hot.security.SecurityUtils;
import com.spice.hot.service.OrderService;
import com.spice.hot.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * REST controller for managing Order.
 */
@RestController
@RequestMapping("/api")
public class OrderResource {

	private final Logger log = LoggerFactory.getLogger(OrderResource.class);
	String[] roles = {AuthoritiesConstants.OPERATOR	,AuthoritiesConstants.ADMIN };
	@Inject
	private OrderService orderService;

	/**
	 * POST /orders -> Create a new order.
	 */
	@RequestMapping(value = "/orders", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order)
			throws URISyntaxException {
		log.debug("REST request to save Order : {}", order);
		if (order.getId() != null) {
			return ResponseEntity
					.badRequest()
					.headers(
							HeaderUtil.createFailureAlert("order", "idexists",
									"A new order cannot already have an ID"))
					.body(null);
		}
		Order result = orderService.save(order);
		return ResponseEntity
				.created(new URI("/api/orders/" + result.getId()))
				.headers(
						HeaderUtil.createEntityCreationAlert("order", result
								.getId().toString())).body(result);
	}

	/**
	 * PUT /orders -> Updates an existing order.
	 */
	@RequestMapping(value = "/orders", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Order> updateOrder(@Valid @RequestBody Order order)
			throws URISyntaxException {
		log.debug("REST request to update Order : {}", order);
		if (order.getId() == null) {
			return createOrder(order);
		}
		Order result = orderService.save(order);
		return ResponseEntity
				.ok()
				.headers(
						HeaderUtil.createEntityUpdateAlert("order", order
								.getId().toString())).body(result);
	}

	/**
	 * GET /orders -> get all the orders.
	 */
	@RequestMapping(value = "/orders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public List<Order> getAllOrders() {
		log.debug("REST request to get all Orders");
		// Allow to see all the orders for Operator and Admin

		// log.debug("login " + SecurityUtils.getCurrentUserLogin());
		// log.debug("user auth "
		// + SecurityUtils.getCurrentUser().getAuthorities().toString());
		// boolean result = SecurityUtils.isCurrentUserInRole(roles);
		// log.debug("result "+result);
		if (SecurityUtils.isCurrentUserInRole(roles)) {

			return orderService.findAll();
		} else {
			return orderService.findByUser(SecurityUtils.getCurrentUserLogin());
		}
	}

	/**
	 * GET /orders -> get all the orders by user.
	 */

	// @RequestMapping(value = "/orders/my", method = RequestMethod.GET,
	// produces = MediaType.APPLICATION_JSON_VALUE)
	// @Timed
	// public List<Order> getAllOrdersByUser() {
	// log.debug("REST request to get all Orders by user "
	// + SecurityUtils.getCurrentUserLogin());
	// return orderService.findByUser(SecurityUtils.getCurrentUserLogin());
	// }

	/**
	 * GET /orders/:id -> get the "id" order.
	 */
	@RequestMapping(value = "/orders/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Order> getOrder(@PathVariable String id) {
		log.debug("REST request to get Order : {}", id);
		Order order = orderService.findOne(id);
		return Optional.ofNullable(order)
				.map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /orders/:id -> delete the "id" order.
	 */
	@RequestMapping(value = "/orders/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
		log.debug("REST request to delete Order : {}", id);
		orderService.delete(id);
		return ResponseEntity
				.ok()
				.headers(
						HeaderUtil.createEntityDeletionAlert("order",
								id.toString())).build();
	}
}
