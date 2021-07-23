package com.cognixia.jump.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Review;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Integer> {

	
}

