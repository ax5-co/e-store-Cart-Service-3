package com.execise.estore.estore.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.execise.estore.estore.entity.Cart;
import com.execise.estore.estore.entity.User;
import com.execise.estore.estore.exception.EStoreException;
import com.execise.estore.estore.repository.UserRepository;
import com.execise.estore.estore.service.CartService;
import com.execise.estore.estore.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartService cartService;
	
	@Override
	public ResponseEntity<Object> save(User user) {
		userRepository.save(user);
		cartService.createCartForUser(user);
		
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}

	@Override
	public User getUser(User user) {
		
		return userRepository.findById(user.getId()).orElseThrow(
				() -> new EStoreException("could not retrieve the user with id-"
						+user.getId()));
	}



}
