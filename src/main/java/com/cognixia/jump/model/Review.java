package com.cognixia.jump.model;

import java.sql.Date;

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
	private Long reviewId;
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName = "id")
	@JsonBackReference(value = "firstParent")
	private User user;

	@ManyToOne
	@JoinColumn(name="restaurant_id", referencedColumnName = "id")
	@JsonBackReference(value = "secondParent")
	private Restaurant restaurant;
	

	@Column(columnDefinition="TEXT")
	private String review;
	
	@Column(columnDefinition = "double default 0.0")
	private double rating;
	
	@NotNull
	private Date date;

	public Review() {
		this(-1L, new User(), new Restaurant(), "", 0.0, new Date(System.currentTimeMillis()));
	}

	public Review(Long reviewId, User user, Restaurant restaurant, String review, double rating, Date date) {
		super();
		this.reviewId = reviewId;
		this.user = user;
		this.restaurant = restaurant;
		this.review = review;
		this.rating = rating;
		this.date = date;
	}

	public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Review [reviewId=" + reviewId + ", user=" + user + ", restaurant=" + restaurant + ", review=" + review
				+ ", rating=" + rating + ", date=" + date + "]";
	}
	
}

