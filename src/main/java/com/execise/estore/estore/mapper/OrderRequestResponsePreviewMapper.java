package com.execise.estore.estore.mapper;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.execise.estore.estore.dto.OrderResponse;
import com.execise.estore.estore.entity.CustomerOrder;
import com.execise.estore.estore.exception.EStoreException;
import com.execise.estore.estore.service.AddressService;
import com.execise.estore.estore.service.OrderItemService;

@Component
public class OrderRequestResponsePreviewMapper {

	@Autowired
	private AddressService addressService;
	
	@Autowired
	private OrderItemService orderItemService;
	
	public OrderResponse mapOrderToResponse(CustomerOrder order) {
		
		try {
			OrderResponse response = new OrderResponse();
			response.setOrderNumber(order.getNumber());
			response.setCreatedAt(order.getCreatedAt());
			response.setCartTotal(order.getCartTotal());
			response.setShippingTotal(order.getShippingTotal());
			response.setOrderTotal(order.getOrderTotal());
			response.setShippingAddressSummary(addressService.getSummary(
					order.getShippingAddress().getId()));
			response.setPaymentMethodName(order.getPaymentMethod().getMethodName());
			response.setOrderItems(new HashSet<>(orderItemService.generateOrderItemsPreviewList(
					order.getUser(), order.getSourceCart().getUuid())));
			response.setUsername(order.getUser().getUserName());
			
			return response;
		} catch (Exception e) {
			throw new EStoreException("Order response Could not be generated"
					+ "from the order Number - "+order.getNumber() );
		}		
	}
}
