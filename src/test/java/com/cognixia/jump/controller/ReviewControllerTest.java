package com.cognixia.jump.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
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
 * 		more exception cases
 * 
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

	private final String STARTING_URI = "http://localhost:8080/api/";

	@Autowired
	private MockMvc mockMvc;

	// @MockBean
	// private CarService service;

	// @InjectMocks
	@MockBean
	private ReviewController controller;
	
	
	
	@Test
	void getUserReviewsListById_success() throws Exception {
		long id = 1;
		User user = new User(1l, "user123", "123", Role.ROLE_USER, new ArrayList<Review>());
		Restaurant restaurant = new Restaurant(1L, "McDonalds", "fast food", "123 address", "5551234567", 3.3,
				new ArrayList<Review>());
		String uri = STARTING_URI + "user/id/{userId}/reviews";

		List<Review> mockReview = 
				Arrays.asList(
						new Review(id, user, restaurant, "review", 4.4, new Date(System.currentTimeMillis())
						)
						);

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);

		ResponseEntity<List<Review>> review = new ResponseEntity<>(mockReview, header, HttpStatus.OK);

		when(controller.getUserReviewsListById(id)).thenReturn(review);

		mockMvc.perform(get(uri, id))
		//.andDo(print()) // print of the respnse
		.andExpect(status().isOk()) // check I have a 200 status code
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)) // check if json is returned
		.andExpect(jsonPath("$.length()").value(review.getBody().size())) // checks size of array of
																				// objects returned
		.andExpect(jsonPath("$[0].reviewId").value(review.getBody().get(0).getReviewId()))
		.andExpect(jsonPath("$[0].review").value(review.getBody().get(0).getReview()))
		.andExpect(jsonPath("$[0].rating").value(review.getBody().get(0).getRating()))
		.andExpect(jsonPath("$[0].date").value(review.getBody().get(0).getDate().toString()))
		;


		verify(controller, times(1)).getUserReviewsListById(id);
		verifyNoMoreInteractions(controller);

	}
	
	@Test
	void getRestaurantReviewsListById_success() throws Exception {
		long id = 1;
		User user = new User(1l, "user123", "123", Role.ROLE_USER, new ArrayList<Review>());
		Restaurant restaurant = new Restaurant(1L, "McDonalds", "fast food", "123 address", "5551234567", 3.3,
				new ArrayList<Review>());
		String uri = STARTING_URI + "/restaurant/id/{restaurantId}/reviews";

		List<Review> mockReview = 
				Arrays.asList(
						new Review(id, user, restaurant, "review", 4.4, new Date(System.currentTimeMillis())
						)
						);

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);

		ResponseEntity<List<Review>> review = new ResponseEntity<>(mockReview, header, HttpStatus.OK);

		when(controller.getRestaurantsReviewsListById(id)).thenReturn(review);

		mockMvc.perform(get(uri, id))
		//.andDo(print()) // print of the respnse
		.andExpect(status().isOk()) // check I have a 200 status code
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)) // check if json is returned
		.andExpect(jsonPath("$.length()").value(review.getBody().size())) // checks size of array of
																				// objects returned
		.andExpect(jsonPath("$[0].reviewId").value(review.getBody().get(0).getReviewId()))
		.andExpect(jsonPath("$[0].review").value(review.getBody().get(0).getReview()))
		.andExpect(jsonPath("$[0].rating").value(review.getBody().get(0).getRating()))
		.andExpect(jsonPath("$[0].date").value(review.getBody().get(0).getDate().toString()))
		;

		verify(controller, times(1)).getRestaurantsReviewsListById(id);
		verifyNoMoreInteractions(controller);

	}
	
	
}
