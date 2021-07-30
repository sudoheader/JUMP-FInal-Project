package com.cognixia.jump.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Review;
import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;

/*
 * TODO:
 * 		
 * 
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

	private final String STARTING_URI = "http://localhost:8080/api/";
	
	@Autowired
	private MockMvc mockMvc;
	
	//@MockBean
	//private CarService service;
	
	//@InjectMocks
	@MockBean
	private UserController controller;
	
	@Test
	void testGetAllUsers_success() throws Exception {
		
		// set up
		String uri = STARTING_URI + "users";
		
		// mock data that will be returned when performing
		// the get request
		List<User> mockUsers = Arrays.asList(
					new User(1l, "user123", "123", Role.ROLE_USER, new ArrayList<Review>()),
					new User(2l, "user456", "456", Role.ROLE_USER, new ArrayList<Review>()),
					new User(3l, "user789", "789", Role.ROLE_USER, new ArrayList<Review>())
				);
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		
		ResponseEntity<List<User>> users = new ResponseEntity<>(
					mockUsers, 
					header,
					HttpStatus.OK
				);
		
		
		// tell Mockito, when the service gets called, return this
		when( controller.getAllUsers()).thenReturn( users );
		
		
		// execute request
		mockMvc.perform( get(uri) )
				.andDo( print() ) // print of the respnse
				.andExpect( status().isOk() ) // check I have a 200 status code
				.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE) ) // check if json is returned
				.andExpect( jsonPath("$.length()").value( users.getBody().size() ) ) // checks size of array of objects returned
				.andExpect( jsonPath("$[0].id").value(users.getBody().get(0).getId()) )
				.andExpect( jsonPath("$[0].username").value(users.getBody().get(0).getUsername()) )
				.andExpect( jsonPath("$[0].password").value(users.getBody().get(0).getPassword()) )
				.andExpect( jsonPath("$[0].role").value(users.getBody().get(0).getRole().toString()) )
				.andExpect( jsonPath("$[0].reviews").value(users.getBody().get(0).getReviews()) )
				
				.andExpect( jsonPath("$[1].id").value(users.getBody().get(1).getId()) )
				.andExpect( jsonPath("$[1].username").value(users.getBody().get(1).getUsername()) )
				.andExpect( jsonPath("$[1].password").value(users.getBody().get(1).getPassword()) )
				.andExpect( jsonPath("$[1].role").value(users.getBody().get(1).getRole().toString()) )
				.andExpect( jsonPath("$[1].reviews").value(users.getBody().get(1).getReviews()) )
				
				.andExpect( jsonPath("$[2].id").value(users.getBody().get(2).getId()) )
				.andExpect( jsonPath("$[2].username").value(users.getBody().get(2).getUsername()) )
				.andExpect( jsonPath("$[2].password").value(users.getBody().get(2).getPassword()) )
				.andExpect( jsonPath("$[2].role").value(users.getBody().get(2).getRole().toString()) )
				.andExpect( jsonPath("$[2].reviews").value(users.getBody().get(2).getReviews()) )
				
				; 
		
		// verification of execution
		verify( controller, times(1) ).getAllUsers();
		verifyNoMoreInteractions(controller);
		
	}
	
	@Test
	void testGetAllUsers_emptyResult() throws Exception {
		String uri = STARTING_URI + "users";
		
		List<User> mockUsers = Arrays.asList();
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		
		ResponseEntity<List<User>> users = new ResponseEntity<>(
					mockUsers, 
					header,
					HttpStatus.OK
				);
		
		when( controller.getAllUsers()).thenReturn( users );
		
		mockMvc.perform( get(uri) )
				.andDo( print() ) 
				.andExpect( status().isOk() ) 
				.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE) ) 
				.andExpect( jsonPath("$.length()").value( users.getBody().size() ) )
				; 
		
		verify( controller, times(1) ).getAllUsers();
		verifyNoMoreInteractions(controller);
		
	}
	
	@Test
	void testGetUserById_success() throws Exception{
		long id = 1;
		String uri = STARTING_URI + "users/{user_id}";
		
		User mockUser = new User(1l, "user123", "123", Role.ROLE_USER, new ArrayList<Review>());
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		
		ResponseEntity<User> user = new ResponseEntity<>(
					mockUser, 
					header,
					HttpStatus.OK
				);
		
		when(controller.getUserById(id)).thenReturn(user);
		
		mockMvc.perform( get(uri, id) )
		.andDo(print())
		.andExpect( status().isOk() )
		.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE) )
		.andExpect( jsonPath("$.id").value(user.getBody().getId()) )
		.andExpect( jsonPath("$.username").value(user.getBody().getUsername()) )
		.andExpect( jsonPath("$.password").value(user.getBody().getPassword()) )
		.andExpect( jsonPath("$.role").value(user.getBody().getRole().toString()) )
		.andExpect( jsonPath("$.reviews").value(user.getBody().getReviews()) )
		;

		verify(controller, times(1)).getUserById(id);
		verifyNoMoreInteractions(controller);	
		
	}
	
	@Test
	void testGetUserById_badId() throws Exception{
		long id = -1;
		String uri = STARTING_URI + "users/{user_id}";
		when(controller.getUserById(id)).thenThrow(new ResourceNotFoundException(""));
		
		mockMvc.perform(get(uri, id))
		 .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ResourceNotFoundException));
		
	}
	
	@Test
	void testAddUser_success() throws Exception{
		String uri = STARTING_URI + "users";
		String json = 
				"{\n"
				+ " \"id\": -1,\n"
				+ " \"username\": \"usernametest\",\n"
				+ " \"password\": \"passwordtest\",\n"
				+ " \"role\": \"ROLE_USER\",\n"
				+ " \"reviews\": []\n"
				+ "}";
		
		User mockUser = new User(1l, "usernametest", "passwordtest", Role.ROLE_USER, new ArrayList<Review>());
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		
		ResponseEntity<User> user = new ResponseEntity<>(
					mockUser, 
					header,
					HttpStatus.CREATED
				);
		
		when(controller.addUser(Mockito.any(User.class))).thenReturn(user);
		
		mockMvc.perform(post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect( jsonPath("$.id").value(user.getBody().getId()) )
			.andExpect( jsonPath("$.username").value(user.getBody().getUsername()) )
			.andExpect( jsonPath("$.password").value(user.getBody().getPassword()) )
			.andExpect( jsonPath("$.role").value(user.getBody().getRole().toString()) )
			.andExpect( jsonPath("$.reviews").value(user.getBody().getReviews()) )
		;
	}
}











