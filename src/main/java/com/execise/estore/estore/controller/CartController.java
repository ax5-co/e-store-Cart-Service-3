package com.execise.estore.estore.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.execise.estore.estore.entity.Cart;
import com.execise.estore.estore.repository.CartRepository;
import com.execise.estore.estore.service.CartService;
import com.execise.estore.estore.service.implementations.AuthService;

import lombok.extern.slf4j.Slf4j;

@RestController

@Slf4j
@RequestMapping("api/cart/")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private AuthService authService;
	
	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> viewCart(
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "6") int size,
			@RequestParam(defaultValue = "createdAt,desc") String[] sort) {
		
		Map<String, Object> serviceResponse= cartService.getCartResponse(page, size, sort);

		Map<String, Object> response = new HashMap<>();
		
		response.putAll(serviceResponse);

		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	
	
	@PostMapping("checkout/")
	public ResponseEntity<Map<String, Object>> checkoutCartToOrder(){
		
		//TODO: I made this in order/checkout, in OrderController
		//this is enough, right?
		
		return null;
	}
	
	private List<Order> resolveSortingListToOrders(
			@RequestParam(defaultValue = "id,asc") String[] sort){
		List<Order> orders = new ArrayList<Order>();

	      if (sort[0].contains(",")) {
	        // will sort more than 2 fields
	        // sortOrder="field, direction"
	        for (String sortOrder : sort) {
	          String[] _sort = sortOrder.split(",");
	          orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
	        }
	      } else {
	        // sort=[field, direction]
	        orders.add(new Order(getSortDirection(sort[1]), sort[0]));
	      }
	      return orders;
	}
	
	private Sort.Direction getSortDirection(String direction) {
		//when the mapping accepts: ?sort=column1,direction1&sort=column2,direction2
		//such that each directionX="asc"/"desc"
		//this function translates this directionX into a Sort.Direction.value.
		//which we use in the functions above to determine the sorting direction
		//so a uri like: 
		//http://localhost:8080/e-store/products?size=6&sort=category_id,asc&sort=id,desc
		//will be resolved to identify the orders of: category_id (ascending) then id (descending)
		
	    if (direction.equals("asc")) {
	      return Sort.Direction.ASC;
	    } else if (direction.equals("desc")) {
	      return Sort.Direction.DESC;
	    }

	    return Sort.Direction.ASC;
	  }
	@GetMapping("/test")
	public List<Cart> testCarts(){
		return cartRepository.findTop10ByUser(authService.getCurrentUser());
	}
}
