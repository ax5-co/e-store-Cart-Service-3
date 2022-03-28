package com.execise.estore.estore.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.execise.estore.estore.dto.CartItemRequest;
import com.execise.estore.estore.dto.CartItemResponse;
import com.execise.estore.estore.entity.CartItem;
import com.execise.estore.estore.entity.User;
import com.execise.estore.estore.service.CartItemService;
import com.execise.estore.estore.service.CartService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/cart/item")
public class CartItemController {

	@Autowired
	private CartItemService cartItemService;

	@GetMapping("/{uuid}")
	public ResponseEntity<Object> getOneCartItem(@PathVariable UUID uuid) {

		CartItemResponse item = cartItemService.getCartItemResponseByCartItemUuid(uuid);

		return new ResponseEntity<>(item, HttpStatus.OK);

	}

	@DeleteMapping("/{uuid}")
	public ResponseEntity<Object> removeOneCartItem(@PathVariable UUID uuid) {

		cartItemService.removeCartItemByCartItemUuid(uuid);

		return new ResponseEntity<>(HttpStatus.OK);

	}

	@PutMapping("/{uuid}/quantity/{newQuantity}")
	public ResponseEntity<Object> updateQuantityCartItem(@PathVariable UUID uuid, @PathVariable int newQuantity) {

		CartItem item = cartItemService.updateCartItemQuantityByCartItemUuid(uuid, newQuantity);

		return new ResponseEntity<>(item, HttpStatus.ACCEPTED);
	}

	@PostMapping("/")
	public ResponseEntity<Object> addItemToCart(@RequestBody CartItemRequest cartItemRequest) {

		CartItemResponse item = cartItemService.addCartItemToCart(cartItemRequest);

		return new ResponseEntity<>(item, HttpStatus.CREATED);
	}

	@PostMapping("/{sku}/{quantity}")
	public ResponseEntity<Object> addItemToCartBySku(@PathVariable String sku, @PathVariable int quantity) {

		CartItemResponse item = cartItemService.addCartItemToCart(sku, quantity);

		return new ResponseEntity<>(item, HttpStatus.CREATED);
	}

	// TODO: moveItemToWishlist.
}