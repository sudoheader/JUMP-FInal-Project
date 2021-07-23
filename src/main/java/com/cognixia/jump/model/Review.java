package com.cognixia.jump.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reviewId;
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName = "id")
	@JsonBackReference
	private User user;

	@ManyToOne
	@JoinColumn(name="restaurant_id", referencedColumnName = "id")
	@JsonBackReference
	private Restaurant restaurant;
	

	@Column(columnDefinition="TEXT")
	private String review;
	
	@Column(columnDefinition = "double default 0.0")
	private double rating;

	public Review() {
		
	}
	
	public Review(Integer reviewId, User user, Restaurant restaurant, @NotNull String review,
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "ReviewModel [reviewId=" + reviewId + ", user=" + user + ", restaurant=" + restaurant + ", review="
				+ review + ", rating=" + rating + "]";
	}
	
	
}

