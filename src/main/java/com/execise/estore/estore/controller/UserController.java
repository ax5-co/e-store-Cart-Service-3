package com.execise.estore.estore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.execise.estore.estore.entity.User;
import com.execise.estore.estore.repository.UserRepository;

@RestController
@RequestMapping("api/")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/register")
	public String processRegister(
			@RequestBody User user) {
		//mean we still get the passord in the POST request 
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = user.getPasswordHash();
		user.setPasswordHash(encoder.encode(rawPassword));
		
		userRepository.save(user);
		
		return "Registered "+user.getUserName();
	}
	
	@GetMapping("/list-users")
	public List<User> viewUsersList() {
		return userRepository.findAll();
	}
}
