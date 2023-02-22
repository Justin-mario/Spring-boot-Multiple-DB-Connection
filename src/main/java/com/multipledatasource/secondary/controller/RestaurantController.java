package com.multipledatasource.secondary.controller;

import com.multipledatasource.secondary.models.Restaurant;
import com.multipledatasource.secondary.repo.RestaurantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestaurantController {
	
	@Autowired
	RestaurantRepository restaurantRepo;
	
	@GetMapping("/restaurtants")
	List<Restaurant> getRestaurants() {
		return restaurantRepo.findAll();
	}
	
	@PostMapping(path = "/restaurtants")
	List<Restaurant> addRestaurant(@RequestBody Restaurant restaurant){
		restaurantRepo.save(restaurant);
		return restaurantRepo.findAll();
	
	}

}
