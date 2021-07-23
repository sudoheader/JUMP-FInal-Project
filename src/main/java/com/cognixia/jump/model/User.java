package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.ArrayList;
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
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	public static enum Role {
		ROLE_USER, ROLE_ADMIN
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@OneToMany( mappedBy = "user", cascade = CascadeType.ALL )
	@JsonManagedReference
	private List<Review> userReviews;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Review> reviews;

	public User() {
		this(-1L, "N/A", "N/A", Role.ROLE_USER, new ArrayList<Review>());
	}

	public User(Long id, String username, String password, Role role, List<Review> reviews) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.reviews = reviews;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	
	public void newReview() {
		
		for(int i = 0; i < userReviews.size(); i++) {
			userReviews.get(i).setReviewId(-1);							// within ReviewModel
		}
		
	}

	@Override
	public String toString() {
		return "UserModel [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role
				+ ", reviews=" + reviews + "]";
	}
	
}
