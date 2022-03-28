package com.execise.estore.estore.service.implementations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.execise.estore.estore.dto.CartItemResponse;
import com.execise.estore.estore.entity.Cart;
import com.execise.estore.estore.entity.CartItem;
import com.execise.estore.estore.entity.CartStatus;
import com.execise.estore.estore.entity.User;
import com.execise.estore.estore.exception.EStoreException;
import com.execise.estore.estore.mapper.CartItemANDCartItemResponseRequestMapper;
import com.execise.estore.estore.repository.CartItemRepository;
import com.execise.estore.estore.repository.CartRepository;
import com.execise.estore.estore.service.CartItemService;
import com.execise.estore.estore.service.CartService;
import com.execise.estore.estore.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class CartServiceImpl implements CartService {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private CartItemANDCartItemResponseRequestMapper itemAndReponseMapper;
	
	private UserService userService;
	
	@Override
	public Map<String, Object> getCartResponse(int page, int size, String[] sort) {

		Map<String, Object> response = new HashMap<>();
		
		User user = authService.getCurrentUser();
		
		Cart cart = this.getActiveCartByUser(user);
		
		if(cart == null) {
			String message = "No items in cart yet, no Cart Object found";
			response.put("message", message);
			return response;
		}
		List<Order> sortingOrders = resolveSortingListToOrders(sort);
		Pageable pagingSort = PageRequest.of(page, size, Sort.by(sortingOrders));
		
		List<CartItemResponse> cartItems = new ArrayList<CartItemResponse>();
		Page<CartItem> pageCartItems = cartItemRepository.findAllByCart(cart, pagingSort);
		
		cartItems.addAll(itemAndReponseMapper.mapToResponseList(pageCartItems.getContent()));
		
		
		
		
		
		response.put("cartItems", cartItems);
		response.put("cartTotal", getCartTotal(cart));
		response.put("cartUUID", cart.getUuid());
		response.put("currentPage", pageCartItems.getNumber());
		response.put("totalItems", pageCartItems.getTotalElements());
		response.put("totalPages", pageCartItems.getTotalPages());

		return response;
	}

	@Override
	public BigDecimal getCartTotal(Cart cart) {
		List<CartItem> avialableCartItems = 
				cartItemService.getInStockCartItemsByCart(cart);
		
		BigDecimal cartTotal = BigDecimal.ZERO;
		BigDecimal itemPrice = BigDecimal.ZERO;
		for (CartItem item : avialableCartItems) {
			itemPrice = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
			cartTotal=cartTotal.add(itemPrice);
		}
		
		if(!(cart.getTotal().equals(cartTotal))) {
			cart.setTotal(cartTotal);
			cartRepository.save(cart);
		}
		return cartTotal;
	}


	@Override
	public Cart getCartByUser(User user) {
		return cartRepository.findCartByUser(user)
		.orElseThrow(() -> new EStoreException("Cart  Not Found for"
				+ " username - " + user.getUserName()));
		
	}
	
	@Override
	public Cart getActiveCartByUser(User user) {
		return cartRepository.findCartByUserAndIsActive(user, true)
		.orElse(null);
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

	@Override
	public Cart createCartForUser(User user) {
		Cart cart = new Cart();
		cart.setActive(true);
		cart.setCartStatus(CartStatus.OPEN);
		cart.setCreatedAt(new Date());
		cart.setTotal(BigDecimal.ZERO);
		cart.setUser(user);
		cart.setUuid(UUID.randomUUID());
		
		cartRepository.save(cart);
		return cart;
	}

	@Override
	public Cart getCartByUuidAndUser(UUID cartUuid, User user) {
		
		Cart cart = cartRepository.findByUuidAndUser(cartUuid, user).
				orElseThrow(
						() -> new EStoreException("Cannot find "
								+ "Cart of UUID - "+ cartUuid
								+" of User - " + user.getUserName()));
		
		return cart;
	}

	@Override
	public void finalizeCheckout(Cart cart) {
		// here, we change cart status to Completed or so
		// change cart.isActive to false
		// in the corresponding user entity, we remove his cart connection to this cart
		//but the current design does not have a Cart in User.
		
		cart.setActive(false);
		cart.setCartStatus(CartStatus.ORDERED);

	}


}
