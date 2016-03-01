package com.spice.hot.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codahale.metrics.annotation.Timed;
import com.spice.hot.domain.Dish;
import com.spice.hot.service.DishService;
import com.spice.hot.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Dish.
 */
@RestController
@RequestMapping("/api")
public class DishResource {

    private final Logger log = LoggerFactory.getLogger(DishResource.class);
        
    @Inject
    private DishService dishService;
    
    /**
     * POST  /dishs -> Create a new dish.
     */
    @RequestMapping(value = "/dishs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @MessageMapping("/newDish")
    @SendTo("/topic/newDish")
    @Timed
    public Dish createDish(@Valid @RequestBody Dish dish) throws URISyntaxException {
        log.debug("REST request to save Dish : {}", dish);
        //log.debug("test picture"+file.getOriginalFilename());
//        if (dish.getId() != null) {
//            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("dish", "idexists", "A new dish cannot already have an ID")).body(null);
//        }
        Dish result = dishService.save(dish);
//        return ResponseEntity.created(new URI("/api/dishs/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert("dish", result.getName().toString()))
//            .body(result);
        return result;
    }

    /**
     * PUT  /dishs -> Updates an existing dish.
     */
    @RequestMapping(value = "/dishs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Dish updateDish(@Valid @RequestBody Dish dish) throws URISyntaxException {
        log.debug("REST request to update Dish : {}", dish);
        if (dish.getId() == null) {
            return createDish(dish);
        }
        Dish result = dishService.save(dish);
        return result;
    }

    /**
     * GET  /dishs -> get all the dishs.
     */
    @RequestMapping(value = "/dishs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Dish> getAllDishs() {
        log.debug("REST request to get all Dishs");
        return dishService.findAll();
            }
    
    
    /**
     * GET  /dishs/:slug/catalog -> get all the dishs by category.
     */
    @RequestMapping(value = "/dishs/{slug}/catalog",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Dish> getAllDishsBySlug(@PathVariable String slug) {
        log.debug("REST request to get all Dishs by slug");
        return dishService.findBySlug(slug);
            }

    /**
     * GET  /dishs/:id -> get the "id" dish.
     */
    @RequestMapping(value = "/dishs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Dish> getDish(@PathVariable String id) {
        log.debug("REST request to get Dish : {}", id);
        Dish dish = dishService.findOne(id);
        return Optional.ofNullable(dish)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dishs/:id -> delete the "id" dish.
     */
    @RequestMapping(value = "/dishs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDish(@PathVariable String id) {
        log.debug("REST request to delete Dish : {}", id);
        dishService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dish", id.toString())).build();
    }
}
