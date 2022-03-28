package com.execise.estore.estore.controller;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.execise.estore.estore.dto.OrderItemPreview;
import com.execise.estore.estore.dto.OrderPreview;
import com.execise.estore.estore.dto.OrderResponse;
import com.execise.estore.estore.dto.myFatoorah.MyFatoorahPaymentExecutionRequest;
import com.execise.estore.estore.dto.myFatoorah.MyFatoorahPaymentExecutionResponse;
import com.execise.estore.estore.entity.CustomerOrder;
import com.execise.estore.estore.entity.User;
import com.execise.estore.estore.mapper.MyFatoorahDtoMappers;
import com.execise.estore.estore.repository.OrderRepository;
import com.execise.estore.estore.repository.UserRepository;
import com.execise.estore.estore.service.OrderItemService;
import com.execise.estore.estore.service.OrderService;
import com.execise.estore.estore.service.implementations.AuthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MyFatoorahDtoMappers myFatoorahDtoMappers;

	@GetMapping("/order-preview/{cartUuid}/{addressId}/{paymentMethodName}")
	public ResponseEntity<OrderPreview> previewOrderBeforeCheckout(
			@PathVariable UUID cartUuid,
			@PathVariable Long addressId,
			@PathVariable String paymentMethodName){
		
		List<OrderItemPreview> itemsPreview = 
				orderItemService.generateOrderItemsPreviewList(
						authService.getCurrentUser(), cartUuid);
		
		OrderPreview orderPreview =
				orderService.generateOrderPreviewFromItemsPreview(
				itemsPreview, authService.getCurrentUser(), cartUuid,
				addressId, paymentMethodName);
		
		return new ResponseEntity<>(orderPreview, HttpStatus.OK);
		
	}
	
	@PostMapping("checkout/{cartUuid}/{addressId}/{paymentMethodName}")
	public ResponseEntity<OrderResponse> checkoutByCartAddressPaymentMethod(
			@PathVariable UUID cartUuid,
			@PathVariable Long addressId,
			@PathVariable String paymentMethodName){
		
		OrderResponse createdOrderResponse= orderService.checkoutOrder(
				authService.getCurrentUser(), cartUuid,
				addressId, paymentMethodName);
		
		return new ResponseEntity<>(createdOrderResponse, HttpStatus.CREATED); 
		
	}
	
	@GetMapping("/{orderNumber}")
	public ResponseEntity<OrderResponse> getOrderForLoggedInUser(
			@PathVariable UUID orderNumber){
		
		return new ResponseEntity<>(orderService.getOrderByUserAndNumber(
				authService.getCurrentUser(), orderNumber), HttpStatus.OK); 
	}
	
	@GetMapping("/my-orders")
	public ResponseEntity<List<OrderResponse>> getOrdersForLoggedInUser(){
		
		return new ResponseEntity<>(orderService.getOrdersByUser(
				authService.getCurrentUser()), HttpStatus.OK); 
	}
	
	@GetMapping("/test")
	public List<CustomerOrder> testOrderPerformance(){
		Instant beforeUserFetched = Instant.now();
		log.info("Time stamp Before User is fetched: " + beforeUserFetched);
		User user = userRepository.findByUserName("user1").get();
		Instant afterUserFetched = Instant.now();
		log.info("Time stamp After User is fetched: " + afterUserFetched);
		return orderRepository.findAll();
//		List<CustomerOrder> orderes = orderRepository.findTop20ByUser(user);
//		Instant afterOrdersFetched = Instant.now();
//		log.info("Time stamp After User is fetched: " + afterOrdersFetched);
//		
//		log.info("time fetching user = "+ChronoUnit.MILLIS.between(beforeUserFetched, afterUserFetched));
//		log.info("time fetching orders = "+ChronoUnit.MILLIS.between(afterUserFetched, afterOrdersFetched));
//		
//		return orderes;
	}

	@GetMapping("/test-pay")
	public ResponseEntity<Object> testPay(){
		RestTemplate restTemplate = new RestTemplate();
		String executePaymentUrl = "https://apitest.myfatoorah.com/v2/ExecutePayment";
		
		MyFatoorahPaymentExecutionRequest executePaymentRequest = new MyFatoorahPaymentExecutionRequest();
		executePaymentRequest.setInvoiceValue(2.5); //dummy amount
		executePaymentRequest.setPaymentMethodId(1); // dummy paymentMethod id  of k-net
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.add("Authorization", "Bearer rLtt6JWvbUHDDhsZnfpAhpYk4dxYDQkbcPTyGaKp2TYqQgG7FGZ5Th_WD53Oq8Ebz6A53njUoo1w3pjU1D4vs_ZMqFiz_j0urb_BH9Oq9VZoKFoJEDAbRZepGcQanImyYrry7Kt6MnMdgfG5jn4HngWoRdKduNNyP4kzcp3mRv7x00ahkm9LAK7ZRieg7k1PDAnBIOG3EyVSJ5kK4WLMvYr7sCwHbHcu4A5WwelxYK0GMJy37bNAarSJDFQsJ2ZvJjvMDmfWwDVFEVe_5tOomfVNt6bOg9mexbGjMrnHBnKnZR1vQbBtQieDlQepzTZMuQrSuKn-t5XZM7V6fCW7oP-uXGX-sMOajeX65JOf6XVpk29DP6ro8WTAflCDANC193yof8-f5_EYY-3hXhJj7RBXmizDpneEQDSaSz5sFk0sV5qPcARJ9zGG73vuGFyenjPPmtDtXtpx35A-BVcOSBYVIWe9kndG3nclfefjKEuZ3m4jL9Gg1h2JBvmXSMYiZtp9MR5I6pvbvylU_PP5xJFSjVTIz7IQSjcVGO41npnwIxRXNRxFOdIUHn0tjQ-7LwvEcTXyPsHXcMD8WtgBh-wxR8aKX7WPSsT1O8d8reb2aR7K3rkV3K82K_0OgawImEpwSvp9MNKynEAJQS6ZHe_J_l77652xwPNxMRTMASk1ZsJL");
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		HttpEntity<MyFatoorahPaymentExecutionRequest> entity = new HttpEntity<MyFatoorahPaymentExecutionRequest>(executePaymentRequest, headers);
		
		log.info(entity.toString());
				
		ResponseEntity<String> stringResponse = restTemplate.exchange(executePaymentUrl, HttpMethod.POST, entity, String.class);
		
		MyFatoorahPaymentExecutionResponse responseBody = myFatoorahDtoMappers.mapStringToExecutePaymentResponse(stringResponse.getBody());
		ResponseEntity<MyFatoorahPaymentExecutionResponse> response = new ResponseEntity<MyFatoorahPaymentExecutionResponse>(responseBody, HttpStatus.OK);
		
		System.out.println("stringResponse : "+ stringResponse.toString());
		System.out.println("response : "+response.toString());
		
		return ResponseEntity.ok(response);
		
	}

	
	
}
