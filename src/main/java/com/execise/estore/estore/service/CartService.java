package com.execise.estore.estore.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.execise.estore.estore.entity.Cart;
import com.execise.estore.estore.entity.User;

@Service
public interface CartService {

	Map<String, Object> getCartResponse(int page, int size, String[] sort);

	Cart getCartByUser(User user);

	Cart createCartForUser(User user);

	Cart getCartByUuidAndUser(UUID cartUuid, User user);

	BigDecimal getCartTotal(Cart cart);

	void finalizeCheckout(Cart cart);

	Cart getActiveCartByUser(User user);

}