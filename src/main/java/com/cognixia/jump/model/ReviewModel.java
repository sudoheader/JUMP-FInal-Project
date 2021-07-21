package com.cognixia.jump.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class ReviewModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reviewId;
	
	@ManyToOne
	@JoinColumn(name="userId", referencedColumnName = "id")
	@JsonBackReference
	private UserModel user;

	@ManyToOne
	@JoinColumn(name="restaurantId", referencedColumnName = "id")
	@JsonBackReference
	private RestaurantModel restaurant;
	
	@NotNull
	@Column(columnDefinition="varchar(max)")
	private String review;
	
	@Column(columnDefinition = "integer default 0")
	private int rating;

	public ReviewModel() {
		
	}
	
	public ReviewModel(Integer reviewId, UserModel user, RestaurantModel restaurant, @NotNull String review,
			int rating) {
		super();
		this.reviewId = reviewId;
		this.user = user;
		this.restaurant = restaurant;
		this.review = review;
		this.rating = rating;
	}

	public Integer getReviewId() {
		return reviewId;
	}

	public void setReviewId(Integer reviewId) {
		this.reviewId = reviewId;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public RestaurantModel getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(RestaurantModel restaurant) {
		this.restaurant = restaurant;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "ReviewModel [reviewId=" + reviewId + ", user=" + user + ", restaurant=" + restaurant + ", review="
				+ review + ", rating=" + rating + "]";
	}
	
	
}

