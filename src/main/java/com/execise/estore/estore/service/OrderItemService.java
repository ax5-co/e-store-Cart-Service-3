package com.execise.estore.estore.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.execise.estore.estore.dto.OrderItemPreview;
import com.execise.estore.estore.entity.CartItem;
import com.execise.estore.estore.entity.CustomerOrder;
import com.execise.estore.estore.entity.OrderItem;
import com.execise.estore.estore.entity.User;

@Service
public interface OrderItemService {

	List<OrderItemPreview> generateOrderItemsPreviewList(User currentUser, UUID cartUuid);

	void save(Set<OrderItem> orderItems);

	Set<OrderItem> generateOrderItems(CustomerOrder order, Set<CartItem> cartItems);
	
	OrderItem generateOrderItem(CustomerOrder order, CartItem cartItem);

}
