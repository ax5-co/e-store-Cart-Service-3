package com.execise.estore.estore.service.implementations;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.execise.estore.estore.dto.OrderItemPreview;
import com.execise.estore.estore.entity.Cart;
import com.execise.estore.estore.entity.CartItem;
import com.execise.estore.estore.entity.CustomerOrder;
import com.execise.estore.estore.entity.OrderItem;
import com.execise.estore.estore.entity.ProductVariant;
import com.execise.estore.estore.entity.User;
import com.execise.estore.estore.mapper.CartItemOrderItemPreviewMapper;
import com.execise.estore.estore.repository.OrderItemRepository;
import com.execise.estore.estore.service.CartItemService;
import com.execise.estore.estore.service.CartService;
import com.execise.estore.estore.service.OrderItemService;
import com.execise.estore.estore.service.ProductVariantService;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired 
	private CartItemService cartItemService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CartItemOrderItemPreviewMapper orderItemMapper;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private ProductVariantService productVariantService;
	
	@Override
	public List<OrderItemPreview> generateOrderItemsPreviewList(User user, UUID cartUuid) {
		Cart cart = cartService.getCartByUuidAndUser(cartUuid, user);
		Set<CartItem> cartItems = cartItemService.getCartItemsByCart(cart);
		List<OrderItemPreview> itemsPreview = new ArrayList<>();
		for (CartItem cartItem : cartItems) {
			itemsPreview.add(orderItemMapper.mapOrderToPreview(cartItem));
		}
		return itemsPreview;
	}

	@Override
	public void save(Set<OrderItem> orderItems) {
		for (OrderItem orderItem : orderItems) {
			orderItemRepository.save(orderItem);
		}
		
	}

	@Override
	public Set<OrderItem> generateOrderItems(CustomerOrder order, Set<CartItem> cartItems) {
		Set<OrderItem> generatedOrderItems = new HashSet<>();
		
		for (CartItem cartItem : cartItems) {
			generatedOrderItems.add(this.generateOrderItem(order, cartItem));
		}
		
		return generatedOrderItems;
	}

	@Override
	public OrderItem generateOrderItem(CustomerOrder order, CartItem cartItem) {
		
		Cart cart = cartItem.getCart();
		String productName = cartItem.getProduct().getTitle();
		String productColor = cartItem.getProduct().getColor();
		String productVariantSize = cartItem.getProductVariant().getSize();
		ProductVariant productVariant = cartItem.getProductVariant();
		BigDecimal productUnitPrice = cartItem.getUnitPrice();
		int quantity = cartItem.getQuantity();
		BigDecimal itemTotal = productUnitPrice.multiply(BigDecimal.valueOf(quantity));
		Date createdAt =Date.from(Instant.now());
		boolean inStock = cartItem.checkInStock();
		User user = cartItem.getUser();
		
		OrderItem orderItem = new OrderItem();
		
		orderItem.setCart(cart);
		orderItem.setProductName(productName);
		orderItem.setProductColor(productColor);
		orderItem.setProductVariantSize(productVariantSize);
		orderItem.setProductVariant(productVariant);
		orderItem.setProductUnitPrice(productUnitPrice);
		orderItem.setQuantity(quantity);
		orderItem.setItemTotal(itemTotal);
		orderItem.setCreatedAt(createdAt);
		orderItem.setInStock(inStock);
		orderItem.setUser(user);
		orderItem.setCustomerOrder(order);
		
		//HERE, upon creating EACH order Item, 
		//we deduct the stock quantity of the corresponding ProductVariant
		int oldStockQuantity = productVariant.getStockQuantity();
		productVariant.setStockQuantity(oldStockQuantity - quantity);
		productVariantService.save(productVariant);
		
		orderItemRepository.save(orderItem);
	
		return orderItem;
	}

}
