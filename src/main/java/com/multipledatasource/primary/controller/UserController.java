package com.multipledatasource.primary.controller;

import com.multipledatasource.primary.models.UserDetail;
import com.multipledatasource.primary.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/users")
	List<UserDetail> getUsers() {
		return userRepository.findAll();
	}
	
	@PostMapping(path = "/users")
	List<UserDetail> addUser(@RequestBody UserDetail userDetail) throws Exception{
		userRepository.save(userDetail);
		return userRepository.findAll();
	
	}

}
