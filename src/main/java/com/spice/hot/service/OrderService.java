package com.spice.hot.service;

import com.spice.hot.domain.Order;
import com.spice.hot.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Order.
 */
@Service
public class OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderService.class);
    
    @Inject
    private OrderRepository orderRepository;
    
    /**
     * Save a order.
     * @return the persisted entity
     */
    public Order save(Order order) {
        log.debug("Request to save Order : {}", order);
        Order result = orderRepository.save(order);
        return result;
    }

    /**
     *  get all the orders.
     *  @return the list of entities
     */
    public List<Order> findAll() {
        log.debug("Request to get all Orders");
        List<Order> result = orderRepository.findAll();
        return result;
    }
    
    /**
     *  get all the orders by user.
     *  @return the list of entities
     */
    public List<Order> findByUser(String login) {
        log.debug("Request to get all Orders");
        List<Order> result = orderRepository.findByUser(login);
        return result;
    }


    /**
     *  get one order by id.
     *  @return the entity
     */
    public Order findOne(String id) {
        log.debug("Request to get Order : {}", id);
        Order order = orderRepository.findOne(id);
        return order;
    }

    /**
     *  delete the  order by id.
     */
    public void delete(String id) {
        log.debug("Request to delete Order : {}", id);
        orderRepository.delete(id);
    }
}
