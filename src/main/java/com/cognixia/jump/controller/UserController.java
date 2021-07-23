package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.InvalidInputException;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepo;


@RequestMapping("/api")
@RestController
public class UserController {
	
	@Autowired
	UserRepo userRepo;
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getUser() {
		
		return ResponseEntity.status(200)
		 .body(userRepo.findAll());
	}
	
	@GetMapping("/users/{user_id}")
	public ResponseEntity<User> getUsersById(@Valid @PathVariable("user_id") int user_id) throws InvalidInputException {
		
		Optional<User> userOpt = userRepo.findById(user_id);
		
		if (userOpt.isPresent()) {
			return ResponseEntity.status(200)
					 .body(userOpt.get());
		}
		
		else {
			throw new InvalidInputException(user_id);
		}
	}	
	
	@PostMapping("/users")
	public ResponseEntity<Optional<User>> addUser(@Valid @RequestBody User user) throws Exception {
		
		user.setId((long) -1);
		
		user.newReviews();			//Where I left off
		
		User newUser = userRepo.save(user);
		
		return ResponseEntity.status(201)
							 .body(userRepo.findByUsername(user.getUsername()));
	}
	
	
}
