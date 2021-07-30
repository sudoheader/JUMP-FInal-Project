
package com.cognixia.jump.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cognixia.jump.model.Restaurant;
import com.cognixia.jump.model.User;

public interface RestaurantRepo extends JpaRepository<Restaurant, Long>{
	@Transactional
	@Modifying
	@Query("delete from Review t where t.id=:id")
	int deleteRestaurant(@Param("id") Long id);
	
	Optional<Restaurant> findByRestaurantName(String restaurantname);
	
	boolean existsByRestaurantName(String restaurantName);
}
