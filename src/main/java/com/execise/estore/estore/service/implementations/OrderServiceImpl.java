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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.execise.estore.estore.dto.OrderItemPreview;
import com.execise.estore.estore.dto.OrderPreview;
import com.execise.estore.estore.dto.OrderResponse;
import com.execise.estore.estore.dto.myFatoorah.MyFatoorahPaymentExecutionRequest;
import com.execise.estore.estore.dto.myFatoorah.MyFatoorahPaymentExecutionResponse;
import com.execise.estore.estore.entity.Address;
import com.execise.estore.estore.entity.Cart;
import com.execise.estore.estore.entity.CartItem;
import com.execise.estore.estore.entity.CustomerOrder;
import com.execise.estore.estore.entity.OrderItem;
import com.execise.estore.estore.entity.PaymentMethod;
import com.execise.estore.estore.entity.User;
import com.execise.estore.estore.exception.EStoreException;
import com.execise.estore.estore.mapper.OrderRequestResponsePreviewMapper;
import com.execise.estore.estore.repository.OrderRepository;
import com.execise.estore.estore.service.AddressService;
import com.execise.estore.estore.service.CartItemService;
import com.execise.estore.estore.service.CartService;
import com.execise.estore.estore.service.OrderItemService;
import com.execise.estore.estore.service.OrderService;
import com.execise.estore.estore.service.PaymentMethodService;
import com.execise.estore.estore.service.ShippingService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired 
	private CartService cartService;
	
	@Autowired 
	private CartItemService cartItemService;
	
	@Autowired 
	private OrderItemService orderItemService;
	
	@Autowired 	
	private AddressService addressService;
	
	@Autowired 	
	private PaymentMethodService paymentMethodService;
	
	@Autowired 	
	private ShippingService shippingService;
	
	@Autowired
	private OrderRequestResponsePreviewMapper orderMapper;
	
	@Override
	public OrderPreview generateOrderPreviewFromItemsPreview(
			List<OrderItemPreview> itemsPreview, User user,
			UUID cartUuid, Long addressId, String paymentMethodName) {
		
		Cart cart = cartService.getCartByUuidAndUser(cartUuid, user);
		
		if(cartItemService.getInStockCartItemsByCart(cart).size() == 0)
			throw new EStoreException("No stock is available of all cart items!");
		
		BigDecimal shippingTotal = shippingService
								.getShippingFeesForDefaultShipping();
		BigDecimal orderTotal = cart.getTotal().add(shippingTotal);
		String addressSummary = addressService.getSummary(addressId);

		//to throw an exception if this method name is incorrect
		paymentMethodService.findByMethodName(paymentMethodName);

		String username = user.getUserName();
		
		try {
			OrderPreview orderPreview = new OrderPreview(
					Instant.now(), cart.getTotal(), shippingTotal, orderTotal, 
					addressSummary, paymentMethodName, new HashSet<>(itemsPreview),
					username);
			return orderPreview;
		} catch (Exception e) {
			throw new EStoreException("Order preview could not be generated!");
		}
	}

	@Override
	public OrderResponse getOrderByUserAndNumber(User user, UUID orderNumber) {
		CustomerOrder order = orderRepository.findByNumberAndUser(orderNumber, user)
				.orElseThrow(() -> new EStoreException(
						"Order of No.- " + orderNumber + 
						" by user- "+ user.getUserName()+
						" was not found!"));
		
		
		return orderMapper.mapOrderToResponse(order);
		
	}

	@Override
	public List<OrderResponse> getOrdersByUser(User user) {
		List<CustomerOrder> orders = orderRepository.findAllByUser(user);
		List<OrderResponse> ordersResponse = new ArrayList<>();
		for (CustomerOrder order : orders) {
			ordersResponse.add(orderMapper.mapOrderToResponse(order));
		}
		return ordersResponse;
	}

	@Override
	public OrderResponse checkoutOrder(User user, UUID cartUuid, 
			Long addressId, String paymentMethodName) {
		
		CustomerOrder order = new CustomerOrder();
		
		//the order number is a UUID, set to UUID.randomUUID() in Order.java
		
		Cart cart = cartService.getCartByUuidAndUser(cartUuid, user);
		
		if(cartItemService.getInStockCartItemsByCart(cart).size() == 0)
			throw new EStoreException("No stock is available of all cart items!");
		
		Set<CartItem> cartItems = new HashSet<>(cartItemService.getInStockCartItemsByCart(cart));
//		Set<CartItem> cartItems = cart.getCartItems();		
		BigDecimal cartTotal = cartService.getCartTotal(cart);
		BigDecimal shippingTotal = 
				shippingService.getShippingFeesForDefaultShipping();
		BigDecimal orderTotal = cartTotal.add(shippingTotal);
		Address shippingAddress = addressService.getAddressByIdAndUser(addressId, user);
		PaymentMethod paymentMethod = paymentMethodService.findByMethodName(paymentMethodName);
		Set<OrderItem> orderItems = 
				orderItemService.generateOrderItems(order, cartItems);

		order.setSourceCart(cart);
		order.setUser(user);
		order.setCreatedAt(Date.from(Instant.now()));
		order.setCartTotal(cartTotal);
		order.setShippingTotal(shippingTotal);
		order.setOrderTotal(orderTotal);
		order.setShippingAddress(shippingAddress);
		order.setPaymentMethod(paymentMethod);
		order.setOrderItems(orderItems);
		
		orderRepository.save(order);
		
		//TODO, since we already save each orderItem in OrderItemServiceImpl
		//upon generating it by: orderItemService.generateOrderItem(order, cartItem)
		//then, I assume the following line would be creating unnecessary queries.
		orderItemService.save(orderItems);
		
		cartService.finalizeCheckout(cart);
		
		return orderMapper.mapOrderToResponse(order);
	}

	@Override
	public CustomerOrder getOrderByNumber(UUID orderNumber) {
		
		return orderRepository.findByNumber(orderNumber).orElseThrow(
				 () -> new EStoreException("No order of this number (UUID) in Repo!") );
	}
	

	

}
