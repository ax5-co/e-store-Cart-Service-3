package com.execise.estore.estore.service.implementations;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.execise.estore.estore.dto.CartItemRequest;
import com.execise.estore.estore.dto.CartItemResponse;
import com.execise.estore.estore.entity.Cart;
import com.execise.estore.estore.entity.CartItem;
import com.execise.estore.estore.entity.Product;
import com.execise.estore.estore.entity.ProductVariant;
import com.execise.estore.estore.entity.User;
import com.execise.estore.estore.exception.EStoreException;
import com.execise.estore.estore.mapper.CartItemANDCartItemResponseRequestMapper;
import com.execise.estore.estore.repository.CartItemRepository;
import com.execise.estore.estore.service.CartItemService;
import com.execise.estore.estore.service.CartService;
import com.execise.estore.estore.service.ProductVariantService;

import lombok.extern.slf4j.Slf4j;

@Service

@Slf4j
@Transactional
public class CartItemServiceImpl implements CartItemService {

	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private CartItemANDCartItemResponseRequestMapper mapper;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private ProductVariantService productVariantService;

	
	@Override
	public CartItem getCartItemByCartItemUuid(UUID uuid) {
		
		return 
				cartItemRepository.findByUuid(uuid)
			.orElseThrow(() -> new EStoreException("Cart Item Not Found with"
						+ " UUID - " + uuid));
	}
	
	@Override
	public CartItemResponse getCartItemResponseByCartItemUuid(UUID uuid) {
		
		return mapper.mapToResponse(
				cartItemRepository.findByUuid(uuid)
			.orElseThrow(() -> new EStoreException("Cart Item Not Found with"
						+ " UUID - " + uuid))
			);
	}

	@Override
	public void removeCartItemByCartItemUuid(UUID uuid) {
		cartItemRepository.deleteAllByUuid(uuid);
		
	}

	/*
	 * the update quantity works as follows:
	 * the item's quantity is updated whatever stock value is available.
	 * the item's inStock is ckecked and set to true/false
	 * the item is saved to items repo
	 * 
	 * What about the cart total? only when the /cart (GET) is used (cartController)..
	 * .. only then a cartservice method is called to update its total
	 *  by considering the items in that cart whose inStock is true.
	 * */
	
	@Override
	public CartItem updateCartItemQuantityByCartItemUuid(UUID uuid, int newQuantity) {
		CartItem item = getCartItemByCartItemUuid(uuid);
		item.setQuantity(newQuantity);
		if (item.checkInStock())
			item.setInStock(true);
		else
			item.setInStock(false);
		
		cartItemRepository.save(item);
				
		return null;
	}

	@Override
	public CartItemResponse addCartItemToCart(CartItemRequest cartItemRequest) {
		
		User user = authService.getCurrentUser();
		
		Cart cart = cartService.getActiveCartByUser(user);
		if(cart == null)
			cart = cartService.createCartForUser(user);
		
		CartItem item = mapper.mapToCartItem(cartItemRequest, user, cart);
		
		Optional<CartItem> existingItem = cartItemRepository.findByCartAndProductVariant(cart, item.getProductVariant());
		
		if(existingItem.isPresent())
			throw new EStoreException("This item is already in cart!");
		
		if(item.checkInStock())
			item.setInStock(true);
		else
			item.setInStock(false);
		
		cartItemRepository.save(item);
		
		return mapper.mapToResponse(item);
	}

	@Override
	public CartItemResponse addCartItemToCart(String sku, int quantity) {
		User user = authService.getCurrentUser();
		Cart cart = cartService.getActiveCartByUser(user);
		if(cart == null)
			cart = cartService.createCartForUser(user);
		
		ProductVariant productVariant = productVariantService.getVariantBySku(sku);
		Product product = productVariant.getProduct();
		
		
		CartItem item = new CartItem();
		item.setCart(cart);
		item.setCreatedAt(new Date());
		item.setProduct(product);
		item.setProductVariant(productVariant);
		item.setQuantity(quantity);
		item.setSize(productVariant.getSize());
		item.setUnitPrice(product.getPrice());
		item.setUser(user);
		item.setUuid(UUID.randomUUID());
		
	Optional<CartItem> existingItem = cartItemRepository.findByCartAndProductVariant(cart, productVariant);
		
		if(existingItem.isPresent())
			throw new EStoreException("This item is already in cart!");
		//TODO: do not show trace!
		
		if ( item.checkInStock() )
			item.setInStock(true);
		else
			item.setInStock(false);
		
		cartItemRepository.save(item);
		
		return mapper.mapToResponse(item);
	}

	@Override
	public List<CartItem> getInStockCartItemsByCart(Cart cart) {

		List<CartItem> cartItems = cartItemRepository.findAllByCart(cart);
		
		for (CartItem item : cartItems) {
			if(item.checkInStock()) 
				item.setInStock(true);
			else
				item.setInStock(false);
			
			cartItemRepository.save(item);
		}

		return cartItemRepository.findAllByCartAndInStock(cart, true);	
	}

	@Override
	public Set<CartItem> getCartItemsByCart(Cart cart) {
		return new HashSet<>(cartItemRepository.findAllByCart(cart));
	}


}
