package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.InvalidInputException;
import com.cognixia.jump.model.Restaurant;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.RestaurantRepo;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/api")
@RestController
public class RestaurantController {
	
	@Autowired
	RestaurantRepo restaurantRepo;																

	@GetMapping("/restaurants")
	@ApiOperation(value = "Find all restaurants",
	  notes = "Get all restaurant names",
	  response = Restaurant.class)
	public ResponseEntity<List<Restaurant>> getRestaurant() {
		
		return ResponseEntity.status(200)
				 .body(restaurantRepo.findAll());
	}
	
	@GetMapping("/restaurants/{restaurant_id}")
<<<<<<< HEAD
	@ApiOperation(value = "Find restaurant by its id",
	  notes = "Return the restaurant",
	  response = Restaurant.class)
	public ResponseEntity<Restaurant> getRestrauntById(@Valid @PathVariable("restaurant_id") int restaurant_id) throws InvalidInputException {
=======
	public ResponseEntity<Restaurant> getRestrauntById(@Valid @PathVariable("restaurant_id") Long restaurant_id) throws InvalidInputException {
>>>>>>> main
		
		Optional<Restaurant> restaurantOpt = restaurantRepo.findById(restaurant_id);
		
		if (restaurantOpt.isPresent()) {
			return ResponseEntity.status(200)
					 .body(restaurantOpt.get());
		}
		
		else {
			throw new InvalidInputException(restaurant_id);
		}
	}
	
<<<<<<< HEAD
	@DeleteMapping("/restaurants/{restaurant_id}")
	@ApiOperation(value = "Delete a restaurant by id",
	  notes = "Delete restaurant",
	  response = User.class)
	public ResponseEntity<Optional<Restaurant>> deleteTodoById(@Valid @PathVariable("restaurant_id") int restaurant_id) throws InvalidInputException {
=======
	@DeleteMapping("/reviews/{restaurant_id}")
	public ResponseEntity<Optional<Restaurant>> deleteTodoById(@Valid @PathVariable("restaurant_id") Long restaurant_id) throws InvalidInputException {
>>>>>>> main
		
	    Optional<Restaurant> restaurant = restaurantRepo.findById(restaurant_id);
	    
	    restaurantRepo.deleteById(restaurant_id);
	    
	    if (restaurant.isPresent()) {
	    	return ResponseEntity.status(200)
					 .body(restaurant);
	    }

		else {
			throw new InvalidInputException(restaurant_id);
		}

	}
}
