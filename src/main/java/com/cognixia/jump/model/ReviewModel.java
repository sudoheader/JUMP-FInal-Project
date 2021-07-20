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
	private Integer userId;
	
	@NotNull
	@Column(columnDefinition = "integer default 0")
	private int restrauntId;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private String review;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@JsonBackReference
	private int rating;

	public ReviewModel(Integer userId, @NotNull int restrauntId, @NotNull String review, int rating) {
		super();
		this.userId = userId;
		this.restrauntId = restrauntId;
		this.review = review;
		this.rating = rating;
	}
	
	public ReviewModel() {
		this(-1, -1, "", -1);
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public int getRestrauntId() {
		return restrauntId;
	}

	public void setRestrauntId(int restrauntId) {
		this.restrauntId = restrauntId;
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
		return "ReviewModel [userId=" + userId + ", restrauntId=" + restrauntId + ", review=" + review + ", rating="
				+ rating + "]";
	}

	
	

}

