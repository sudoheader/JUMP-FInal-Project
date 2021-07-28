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
import com.cognixia.jump.model.Review;
import com.cognixia.jump.repository.ReviewRepo;

@RequestMapping("/api")
@RestController
public class ReviewController {
	
	@Autowired
	ReviewRepo repo;

	@GetMapping("/reviews")
	public ResponseEntity<List<Review>> getReviews() {
		
		return ResponseEntity.status(200)
				 .body(repo.findAll());
	}
	
	@GetMapping("/reviews/{review_id}")
	public ResponseEntity<Review> getReviewsById(@Valid @PathVariable("review_id") Long review_id) throws InvalidInputException {
		
		Optional<Review> todoOpt = repo.findById(review_id);
	
		
		if (todoOpt.isPresent()) {
			return ResponseEntity.status(200)
					 .body(todoOpt.get());
		}
		
		else {
			throw new InvalidInputException(review_id);
		}
	}
	
	@DeleteMapping("/reviews/{review_id}")
	public ResponseEntity<Optional<Review>> deleteTodoById(@Valid @PathVariable("review_id") Long review_id) throws InvalidInputException {

		
	    Optional<Review> review = repo.findById(review_id);
	    
	    repo.deleteById(review_id);
	    
	    if (review.isPresent()) {
	    	return ResponseEntity.status(200)
					 .body(review);
	    }

		else {
			throw new InvalidInputException(review_id);
		}

	}
}
