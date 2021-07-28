package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.InvalidInputException;
import com.cognixia.jump.model.Restaurant;
import com.cognixia.jump.model.Review;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.RestaurantRepo;

@RequestMapping("/api")
@RestController
public class RestaurantController {
	
	@Autowired
	RestaurantRepo restaurantRepo;

	@GetMapping("/restaurants")
	public ResponseEntity<List<Restaurant>> getRestaurant() {
		
		return ResponseEntity.status(200)
				 .body(restaurantRepo.findAll());
	}
	
	@GetMapping("/restaurants/{restaurant_id}")
	public ResponseEntity<Restaurant> getRestrauntById(@Valid @PathVariable("restaurant_id") Long restaurant_id) throws InvalidInputException {
		
		Optional<Restaurant> restaurantOpt = restaurantRepo.findById(restaurant_id);
		
		if (restaurantOpt.isPresent()) {
			return ResponseEntity.status(200)
					 .body(restaurantOpt.get());
		}
		
		else {
			throw new InvalidInputException(restaurant_id);
		}
	}
	
	@DeleteMapping("/reviews/{restaurant_id}")
	public ResponseEntity<Optional<Restaurant>> deleteTodoById(@Valid @PathVariable("restaurant_id") Long restaurant_id) throws InvalidInputException {
		
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
