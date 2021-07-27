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
	public ResponseEntity<?> getAllUsers() {
		List<User> list = userRepo.findAll();
		
		return ResponseEntity.status(200)
							 .body(list);
	}
	
	@GetMapping("/users/{user_id}")
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
	
	@PostMapping("/users")
	public ResponseEntity<?> addUser(@Valid @RequestBody User user) throws Exception {
		
		user.setId(-1L);
		
		//user.newReviews();			//Where I left off
		
		User newUser = userRepo.save(user);
		
		return ResponseEntity.status(201)
							 .body(newUser);
	}
	
	
}
