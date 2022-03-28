package com.execise.estore.estore.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.execise.estore.estore.entity.PaymentMethod;
import com.execise.estore.estore.service.PaymentMethodService;

@RestController
@RequestMapping("/api/payment/methods")
public class PaymentMethodController {

	@Autowired
	private PaymentMethodService paymentMethodService;
	
	@GetMapping("/")
	public ResponseEntity<List<PaymentMethod>> getAllPaymentMethods(){
		List<PaymentMethod> paymentMethods = paymentMethodService.findAllPaymentMethods();
		return new ResponseEntity<List<PaymentMethod>>(paymentMethods, HttpStatus.OK);
	}
	
	@GetMapping("/names")
	public ResponseEntity<List<String>> getAllPaymentMethodsNames(){
		List<String> paymentMethodsNames = paymentMethodService.findAllPaymentMethodsNames();
		return new ResponseEntity<List<String>>(paymentMethodsNames, HttpStatus.OK);
	}
	
	@GetMapping("/{methodName}/fees")
	public ResponseEntity<String> getPaymentFeesOfMethod(
			@PathVariable String methodName){
		BigDecimal paymentFees = paymentMethodService.findFeesByMethodsName(methodName);
		return new ResponseEntity<String>(paymentFees.toString(), HttpStatus.OK);
	}
	
	
}
