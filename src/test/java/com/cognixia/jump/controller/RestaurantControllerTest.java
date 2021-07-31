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
import com.cognixia.jump.model.Restaurant;
import com.cognixia.jump.model.Review;
import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;

/*
 * TODO:
 * 		figure out how to mock delete requests
 * 
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

	private final String STARTING_URI = "http://localhost:8080/api/";
	
	@Autowired
	private MockMvc mockMvc;
	
	//@MockBean
	//private CarService service;
	
	//@InjectMocks
	@MockBean
	private RestaurantController controller;
	
	@Test
	void testGetAllRestaurants_success() throws Exception {
		
		// set up
		String uri = STARTING_URI + "restaurants";
		
		// mock data that will be returned when performing
		// the get request
		List<Restaurant> mockRestaurants = Arrays.asList(
					new Restaurant(1L, "McDonalds", "fast food", "123 address", "5551234567", 3.3, new ArrayList<Review>()),
					new Restaurant(2L, "Dominos", "pizza", "456 address", "555987654321", 4.5, new ArrayList<Review>())
				);
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		
		ResponseEntity<List<Restaurant>> restaurants = new ResponseEntity<>(
					mockRestaurants, 
					header,
					HttpStatus.OK
				);
		
		
		// tell Mockito, when the service gets called, return this
		when( controller.getAllRestaurants()).thenReturn( restaurants );
		
		
		// execute request
		mockMvc.perform( get(uri) )
				.andDo( print() ) // print of the respnse
				.andExpect( status().isOk() ) // check I have a 200 status code
				.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE) ) // check if json is returned
				.andExpect( jsonPath("$.length()").value( restaurants.getBody().size() ) ) // checks size of array of objects returned
				.andExpect( jsonPath("$[0].id").value(restaurants.getBody().get(0).getId()) )
				.andExpect( jsonPath("$[0].restaurantName").value(restaurants.getBody().get(0).getRestaurantName()) )
				.andExpect( jsonPath("$[0].description").value(restaurants.getBody().get(0).getDescription()) )
				.andExpect( jsonPath("$[0].address").value(restaurants.getBody().get(0).getAddress()) )
				.andExpect( jsonPath("$[0].phoneNumber").value(restaurants.getBody().get(0).getPhoneNumber()) )
				.andExpect( jsonPath("$[0].rating").value(restaurants.getBody().get(0).getRating()) )
				.andExpect( jsonPath("$[0].reviews").value(restaurants.getBody().get(0).getReviews()) )
				
				.andExpect( jsonPath("$[1].id").value(restaurants.getBody().get(1).getId()) )
				.andExpect( jsonPath("$[1].restaurantName").value(restaurants.getBody().get(1).getRestaurantName()) )
				.andExpect( jsonPath("$[1].description").value(restaurants.getBody().get(1).getDescription()) )
				.andExpect( jsonPath("$[1].address").value(restaurants.getBody().get(1).getAddress()) )
				.andExpect( jsonPath("$[1].phoneNumber").value(restaurants.getBody().get(1).getPhoneNumber()) )
				.andExpect( jsonPath("$[1].rating").value(restaurants.getBody().get(1).getRating()) )
				.andExpect( jsonPath("$[1].reviews").value(restaurants.getBody().get(1).getReviews()) )
				; 
		
		// verification of execution
		verify( controller, times(1) ).getAllRestaurants();
		verifyNoMoreInteractions(controller);
		
	}
	
	@Test
	void testGetAllRestaurants_emptyResult() throws Exception {
		String uri = STARTING_URI + "restaurants";
		
		List<Restaurant> mockRestaurants = Arrays.asList();
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		
		ResponseEntity<List<Restaurant>> restaurants = new ResponseEntity<>(
					mockRestaurants, 
					header,
					HttpStatus.OK
				);
		
		when( controller.getAllRestaurants()).thenReturn( restaurants );
		
		mockMvc.perform( get(uri) )
				.andDo( print() ) 
				.andExpect( status().isOk() ) 
				.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE) ) 
				.andExpect( jsonPath("$.length()").value( restaurants.getBody().size() ) )
				; 
		
		verify( controller, times(1) ).getAllRestaurants();
		verifyNoMoreInteractions(controller);
		
	}
	
	
	@Test
	void testGetRestaurantById_success() throws Exception{
		long id = 1;
		String uri = STARTING_URI + "restaurants/id/{restaurant_id}";
		
		Restaurant mockRestaurant = 
				new Restaurant(1L, "McDonalds", "fast food", "123 address", "5551234567", 3.3, new ArrayList<Review>());

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		
		ResponseEntity<Restaurant> restaurant = new ResponseEntity<>(
					mockRestaurant, 
					header,
					HttpStatus.OK
				);
		
		when(controller.getRestaurantById(id)).thenReturn(restaurant);
		
		mockMvc.perform( get(uri, id) )
		.andDo(print())
		.andExpect( status().isOk() )
		.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE) )
		.andExpect( jsonPath("$.id").value(restaurant.getBody().getId()) )
		.andExpect( jsonPath("$.restaurantName").value(restaurant.getBody().getRestaurantName()) )
		.andExpect( jsonPath("$.description").value(restaurant.getBody().getDescription()) )
		.andExpect( jsonPath("$.address").value(restaurant.getBody().getAddress()) )
		.andExpect( jsonPath("$.phoneNumber").value(restaurant.getBody().getPhoneNumber()) )
		.andExpect( jsonPath("$.rating").value(restaurant.getBody().getRating()) )
		.andExpect( jsonPath("$.reviews").value(restaurant.getBody().getReviews()) )
		
		;

		verify(controller, times(1)).getRestaurantById(id);
		verifyNoMoreInteractions(controller);	
		
	}
	
	@Test
	void testGetRestaurantByName_success() throws Exception{
		String name = "McDonalds";
		String uri = STARTING_URI + "restaurants/name/{restaurant_name}";
		
		Restaurant mockRestaurant = 
				new Restaurant(1L, "McDonalds", "fast food", "123 address", "5551234567", 3.3, new ArrayList<Review>());

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		
		ResponseEntity<Restaurant> restaurant = new ResponseEntity<>(
					mockRestaurant, 
					header,
					HttpStatus.OK
				);
		
		when(controller.getRestaurantByName(name)).thenReturn(restaurant);
		
		mockMvc.perform( get(uri, name) )
		.andDo(print())
		.andExpect( status().isOk() )
		.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE) )
		.andExpect( jsonPath("$.id").value(restaurant.getBody().getId()) )
		.andExpect( jsonPath("$.restaurantName").value(restaurant.getBody().getRestaurantName()) )
		.andExpect( jsonPath("$.description").value(restaurant.getBody().getDescription()) )
		.andExpect( jsonPath("$.address").value(restaurant.getBody().getAddress()) )
		.andExpect( jsonPath("$.phoneNumber").value(restaurant.getBody().getPhoneNumber()) )
		.andExpect( jsonPath("$.rating").value(restaurant.getBody().getRating()) )
		.andExpect( jsonPath("$.reviews").value(restaurant.getBody().getReviews()) )
		
		;

		verify(controller, times(1)).getRestaurantByName(name);
		verifyNoMoreInteractions(controller);	
		
	}
	
	
	
	
	@Test
	void testGetRestaurantById_badId() throws Exception{
		long id = -1;
		String uri = STARTING_URI + "restaurants/id/{restaurant_id}";
		when(controller.getRestaurantById(id)).thenThrow(new ResourceNotFoundException(""));
		
		mockMvc.perform(get(uri, id))
		 .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ResourceNotFoundException));
		
	}
	
	@Test
	void testGetRestaurantByName_badName() throws Exception{
		String name = "hkldfjg";
		String uri = STARTING_URI + "restaurants/name/{restaurant_name}";
		when(controller.getRestaurantByName(name)).thenThrow(new ResourceNotFoundException(""));
		
		mockMvc.perform(get(uri, name))
		.andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ResourceNotFoundException));
		
	}
	
	/*
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
	*/
}











