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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.InvalidInputException;
import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Restaurant;
import com.cognixia.jump.model.Review;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.RestaurantRepo;
import com.cognixia.jump.repository.ReviewRepo;
import com.cognixia.jump.repository.UserRepo;

import io.swagger.annotations.ApiOperation;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/api")
@RestController
public class ReviewController {
	
	@Autowired
  ReviewRepo reviewRepo;
	
	@Autowired
	UserRepo userRepo;

	@Autowired
	RestaurantRepo restaurantRepo;
	
	@Autowired
	RestaurantController restaurantController;
  
  @GetMapping("/reviews")
	@ApiOperation(value = "Find all reviews",
	  notes = "Get all reviews",
	  response = Review.class)
	public ResponseEntity<List<Review>> getReviews() {
		
		return ResponseEntity.status(HttpStatus.200)
				 .body(repo.findAll());
	}
	
	//@GetMapping("/reviews/{review_id}")
	//public ResponseEntity<Review> getReviewsById(@Valid @PathVariable("review_id") Long review_id) throws InvalidInputException {

		
		//Optional<Review> todoOpt = repo.findById(review_id);

	@GetMapping("/user/{userId}/reviews")
	public ResponseEntity<List<Review>> getUserReviews(@PathVariable long userId) throws ResourceNotFoundException{
		if(!userRepo.existsById(userId)) {
			throw new ResourceNotFoundException("User with user id " + userId + " not found.");
		}
		List<Review> reviews = userRepo.findById(userId).get().getReviews();
		return ResponseEntity.status(HttpStatus.OK).body(reviews);
	}
	
	@GetMapping("/restaurant/{restaurantId}/reviews")
	public ResponseEntity<List<Review>> getRestaurantsReviews(@PathVariable long restaurantId) throws ResourceNotFoundException{
		if(!restaurantRepo.existsById(restaurantId)) {
			throw new ResourceNotFoundException("Restaurant with id " + restaurantId + " not found.");
		}
		List<Review> reviews = restaurantRepo.findById(restaurantId).get().getReviews();
		return ResponseEntity.status(HttpStatus.OK).body(reviews);
	}
	
	/*
	 * TODO: double check this one
	 */
	@PostMapping("/restaurants/{restaurantId}/reviews")
	public ResponseEntity<Review> addRestaurantReview(@PathVariable long restaurantId, @Valid @RequestBody Review review) throws ResourceNotFoundException{
		review.setReviewId(-1L);
		Review newReview = reviewRepo.save(review); 
		
		//recalculate restaurant rating
		List<Review> reviews = getRestaurantsReviews(restaurantId).getBody();
		double sum = 0;
		for(Review r : reviews) {
			sum += r.getRating();
		}
		double avg = sum/reviews.size();
		
		Restaurant restaurant = (Restaurant) restaurantController.getRestaurantById(restaurantId).getBody();
		restaurant.setRating(avg);
		restaurantRepo.save(restaurant); //update restaurant with new rating
		
		return ResponseEntity.status(HttpStatus.CREATED).body(newReview);
	}
	
}
