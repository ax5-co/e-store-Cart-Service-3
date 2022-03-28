package com.execise.estore.estore.controller;


import static com.execise.estore.estore.Constants.MY_FATOORAH_AUTHORIZATION_HEADER_VALUE;
import static com.execise.estore.estore.Constants.MY_FATOORAH_EXECUTE_PAYMENT_URL;

import java.util.Collections;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.execise.estore.estore.dto.myFatoorah.MyFatoorahPaymentExecutionRequest;
import com.execise.estore.estore.dto.myFatoorah.MyFatoorahPaymentExecutionResponse;
import com.execise.estore.estore.dto.myFatoorah.PaymentModel;
import com.execise.estore.estore.entity.CustomerOrder;
import com.execise.estore.estore.enums.MyFatoorahApiType;
import com.execise.estore.estore.service.OrderService;
import com.execise.estore.estore.service.PaymentRecordService;
import com.execise.estore.estore.service.implementations.PaymentService;
import com.execise.estore.estore.service.implementations.HttpService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/payment")
@Slf4j
public class PaymentController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private PaymentRecordService paymentRecordService;

	@Autowired
	private HttpService<PaymentModel> httpService;
	
	

	@PostMapping("/error")
	public String getErrorPage() {
		return "Sorry, an error occurred during payment, please contact customer service...";
	}

	@PostMapping("/success")
	public String getSuccessPage() {
		// TODO: what should I do on a call back?
		return "Payment Successful!";
	}

	@PostMapping("/pay/{orderNumber}/{paymentMethodId}")
	public ResponseEntity<Object> generatePaymentLink(
			@PathVariable UUID orderNumber,
			@PathVariable int paymentMethodId) {
		
		// current User is not included so any logged-in user can pay any order
		CustomerOrder order = orderService.getOrderByNumber(orderNumber); 
		
		MyFatoorahPaymentExecutionRequest request = 
				paymentService.generateExecutePaymentRequest(order,
				paymentMethodId, MyFatoorahApiType.EXECUTE);

		ResponseEntity<MyFatoorahPaymentExecutionResponse> response = 
				new RestTemplate()
				.exchange(request.getMyFatoorahApiType().getURL(),
						HttpMethod.POST, 
						httpService.initiateRequest(request),
						MyFatoorahPaymentExecutionResponse.class);
		
		paymentRecordService.savePaymentRecord(response.getBody(), order);

		return ResponseEntity.ok(response);
	}
	
	//the following method should be only for Admins, but let's make it for every logged-in user for now
//	@GetMapping("/getStatus/order/{orderNumber}")
//	public ResponseEntity<Object> getPaymentStatus(
//			@PathVariable UUID orderNumber,
//			@RequestParam(defaultValue = "0") int page, 
//			@RequestParam(defaultValue = "3") int size){
//		
//		Pageable paging = PageRequest.of(page, size);
//		Page <GetPaymentStatusResponse> paymentStatusesPage= 
//				paymentService.getAllPaymentStatusForOrderNumber(
//						orderNumber, paging);
//		
//		ResponseEntity<T>
//	}
}
