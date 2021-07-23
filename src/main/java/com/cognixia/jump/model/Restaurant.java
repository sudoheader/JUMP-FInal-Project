package com.cognixia.jump.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Restaurant implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String restaurantName;
	
	@NotNull
	@Column(columnDefinition="TEXT")
	private String description;
	
	@Column(nullable = false, unique = true)
	private String address;
	
	@Column(nullable = false, unique = true)
	private String phoneNumber;
	
	@Column(nullable = false, columnDefinition="double default 0.0")
	private double rating;
	
	@OneToMany( mappedBy = "restaurant", cascade = CascadeType.ALL )
	@JsonManagedReference
	private List<Review> reviews;
	
	public Restaurant() {
		this(-1L, "N/A", "N/A", "N/A", "0000000000", 0.0, new ArrayList<Review>());
	}

	public Restaurant(Long id, String restaurantName, String description, String address, String phoneNumber,
			double rating, List<Review> reviews) {
		super();
		this.id = id;
		this.restaurantName = restaurantName;
		this.description = description;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.rating = rating;
		this.reviews = reviews;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	@Override
	public String toString() {
		return "RestaurantModel [id=" + id + ", restaurantName=" + restaurantName + ", description=" + description
				+ ", address=" + address + ", phoneNumber=" + phoneNumber + ", rating=" + rating + ", reviews="
				+ reviews + "]";
	}
	
}
