package com.cognixia.jump.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cognixia.jump.exception.InvalidInputException;
import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Review;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepo;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	@Autowired
	UserRepo userRepo;

	//READ
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/users")
	@ApiOperation(value = "Find all Users", notes = "Get all user names", response = User.class)
	public ResponseEntity<List<User>> getAllUsers() {

		List<User> list = userRepo.findAll();

		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	//READ
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/users/{user_id}")
	@ApiOperation(value = "Find users by id",
	  notes = "Get the user with a specific id",
	  response = User.class)
	public ResponseEntity<User> getUserById(@Valid @PathVariable("user_id") Long user_id) throws ResourceNotFoundException {
		if(!userRepo.existsById(user_id)) {
			throw new ResourceNotFoundException("User with id " + user_id + " not found");
		}
		
	    User user = userRepo.findById(user_id).get();
	    
	    return ResponseEntity.status(HttpStatus.OK)
	                        .body(user);
	}
	
	//CREATE
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/users")
	@ApiOperation(value = "Add a user", notes = "Initialize a new user", response = User.class)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<User> addUser(@Valid @RequestBody User user) throws Exception {

		user.setId(-1L);
		user.setReviews(new ArrayList<Review>());

		//user.newReview();

		User newUser = userRepo.save(user);

		return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PutMapping("/users")
	@ApiOperation( value = "Update User",
	   notes = "Update user (if exists) in the database",
	   response = User.class)
	public ResponseEntity<User> updateUser(@Valid @RequestBody User user) throws ResourceNotFoundException {
		
		Optional<User> found =  userRepo.findById(user.getId());
		user.setReviews(new ArrayList<Review>());

		if( found.isPresent() ) {
			User updated = userRepo.save(user);
			
			return ResponseEntity.status(HttpStatus.OK)
								 .body(updated);
		}
		else {
			throw new ResourceNotFoundException("User with id " + user.getId() + " not found");
		}
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@DeleteMapping("/users/{user_id}")
	@ApiOperation( value = "Delete User by id",
	   notes = "Delete user (if exists) in the database",
	   response = User.class)
	public ResponseEntity<User> deleteUser(@PathVariable Long user_id) throws ResourceNotFoundException {
		
		Optional<User> user = userRepo.findById(user_id);
		
		if(user.isPresent()) {
			userRepo.deleteById(user_id);
			return ResponseEntity.status(HttpStatus.OK)
								 .body(user.get());
		}
		else {
			throw new ResourceNotFoundException("User with id " + user_id + " not found");
		}
	}

}
