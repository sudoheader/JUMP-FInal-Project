
package com.cognixia.jump.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cognixia.jump.model.Review;

public interface ReviewRepo extends JpaRepository<Review, Long>{
	@Transactional
	@Modifying
	@Query("delete from Review t where t.id=:id")
	int deleteReview(@Param("id") Long id);
}

