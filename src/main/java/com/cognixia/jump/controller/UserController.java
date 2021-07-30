package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepo;

import io.swagger.annotations.ApiOperation;



@RequestMapping("/api")
@RestController
public class UserController {
								
	@Autowired
	UserRepo userRepo;
	
	@GetMapping("/users")					
	@ApiOperation(value = "Find all Users",
	  notes = "Get all user names",
	  response = User.class)
	public ResponseEntity<?> getAllUsers() {
		List<User> list = userRepo.findAll();
		
		return ResponseEntity.status(200)
							 .body(list);
	}
	
	//Read
	@GetMapping("/users/{user_id}")			
	@ApiOperation(value = "Find users by id",
	  notes = "Get the user with a specific id",
	  response = User.class)
	public ResponseEntity<?> getUsersById(@Valid @PathVariable("user_id") Long user_id) throws InvalidInputException {
		
		Optional<User> userOpt = userRepo.findById(user_id);
		
		if (userOpt.isPresent()) {
			return ResponseEntity.status(200)
					 .body(userOpt.get());
		}
		
		else {
			throw new InvalidInputException(user_id);
		}
	}	
	
	//Create
	@PostMapping("/users")
	@ApiOperation(value = "Add a user",
	  notes = "Initialize a new user",
	  response = User.class)
	public ResponseEntity<?> addUser(@Valid @RequestBody User user) throws Exception {
		
		user.setId(-1L);
		
		user.newReview();		
		
		User newUser = userRepo.save(user);
		
		return ResponseEntity.status(201)
							 .body(newUser);
	}
	
	//Update
	@CrossOrigin
	@PutMapping("/update/users")
	public @ResponseBody String updateUser(@RequestBody User updateUser) {
		
		// check if user exists, then update them
		
		Optional<User> found = userRepo.findById(updateUser.getId());
		
		if(found.isPresent()) {
			userRepo.save(updateUser);
			return "Saved: " + updateUser.toString();
		}
		else {
			return "Could not update student, the id = " + updateUser.getId() + " doesn't exist";
		}
		
	}
	
	//Delete
	@DeleteMapping("/delete/users/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable long id) {
		
		Optional<User> found = userRepo.findById(id);
		
		if(found.isPresent()) {
			
			userRepo.deleteById(id);
			
			return ResponseEntity.status(200).body("Deleted User with id = " + id);	
		}
		else {
			return ResponseEntity.status(400)
					.header("User id", id + "")
					.body("User with id = " + id + " not found");
		}
			
	}
	
	
	
}
