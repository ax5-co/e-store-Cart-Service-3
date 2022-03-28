package com.execise.estore.estore.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.execise.estore.estore.entity.Cart;
import com.execise.estore.estore.entity.User;

@Service
public interface UserService {

	ResponseEntity<Object> save(User user);

	User getUser(User user);


}
