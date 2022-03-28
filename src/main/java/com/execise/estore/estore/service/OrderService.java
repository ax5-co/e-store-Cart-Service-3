package com.execise.estore.estore.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.execise.estore.estore.dto.OrderItemPreview;
import com.execise.estore.estore.dto.OrderPreview;
import com.execise.estore.estore.dto.OrderResponse;
import com.execise.estore.estore.entity.CustomerOrder;
import com.execise.estore.estore.entity.User;

@Service
public interface OrderService {

	OrderPreview generateOrderPreviewFromItemsPreview(List<OrderItemPreview> itemsPreview, User currentUser,
			UUID cartUuid, Long addressId, String paymentMethodName);


	OrderResponse getOrderByUserAndNumber(User currentUser, UUID orderNumber);

	List<OrderResponse> getOrdersByUser(User currentUser);

	OrderResponse checkoutOrder(User currentUser, UUID cartUuid, Long addressId, String paymentMethodName);


	CustomerOrder getOrderByNumber(UUID orderNumber);

}
