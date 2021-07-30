package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

	//CREATE
	@PostMapping("/restaurants")
	public void addUser(@Valid @RequestBody Restaurant restaurant) throws Exception {

		restaurant.setId(-1L);

		Restaurant added = restaurantRepo.save(restaurant);

		System.out.println("Added: " + added);
		
	}
	
	//READ
	@GetMapping("/restaurants")
	@ApiOperation(value = "Find all restaurants",
	  notes = "Get all restaurant names",
	  response = Restaurant.class)
	public ResponseEntity<List<Restaurant>> getRestaurant() {
		
		return ResponseEntity.status(200)
				 .body(restaurantRepo.findAll());
	}
	
	//READ
	@GetMapping("/restaurants/{restaurant_id}")
	@ApiOperation(value = "Find restaurant by its id",
	  notes = "Return the restaurant",
	  response = Restaurant.class)
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
	
	//UPDATE
	@PutMapping("/update/restaurants")
	@ApiOperation(value = "Update a restaurant",
			notes = "Restraunt to be updated",
			response = Restaurant.class)
	public @ResponseBody String updateRestaurant(@RequestBody Restaurant updateRestaurant) {
		
		// check if student exists, then update them
		
		Optional<Restaurant> found = restaurantRepo.findById(updateRestaurant.getId());
		
		if(found.isPresent()) {
			restaurantRepo.save(updateRestaurant);
			return "Saved: " + updateRestaurant.toString();
		}
		else {
			return "Could not update student, the id = " + updateRestaurant.getId() + " doesn't exist";
		}
		
	}
	
	//DELETE
	@DeleteMapping("/restaurants/{restaurant_id}")
	@ApiOperation(value = "Delete a restaurant by id",
	  notes = "Delete restaurant",
	  response = User.class)
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
