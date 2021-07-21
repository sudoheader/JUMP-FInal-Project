package com.cognixia.jump.model;
import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class RestaurantModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static enum Role{
		ROLE_USER, ROLE_ADMIN
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String restaurantName;
	
	@Column( nullable = false )
	private String Description;
	
	@Column(nullable = false, unique = true)
	private String address;
	
	@Column(nullable = false, unique = true)
	private int number;
	
	@Column(nullable = false)
	private double rating;
	
	@OneToMany( mappedBy = "restaurant", cascade = CascadeType.ALL )
	@JsonManagedReference
	private List<ReviewModel> reviews;
	
	public RestaurantModel() {
		this(-1L, "N/A", "N/A", "N/A", 0000000000, 0.0);
	}
	
	public RestaurantModel(Long id, String restaurantName, String description, String address, int number,
			double rating) {
		super();
		this.id = id;
		this.restaurantName = restaurantName;
		Description = description;
		this.address = address;
		this.number = number;
		this.rating = rating;
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
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "RestaurantModel [id=" + id + ", restaurantName=" + restaurantName + ", Description=" + Description
				+ ", address=" + address + ", number=" + number + ", rating=" + rating + "]";
	}
	
	
}
