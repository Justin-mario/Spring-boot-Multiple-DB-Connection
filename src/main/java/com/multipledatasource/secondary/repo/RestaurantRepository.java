package com.multipledatasource.secondary.repo;

import com.multipledatasource.secondary.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
