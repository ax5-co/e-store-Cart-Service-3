package com.execise.estore.estore.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.execise.estore.estore.entity.PaymentMethod;

@Service
public interface PaymentMethodService {

	List<String> findAllPaymentMethodsNames();

	BigDecimal findFeesByMethodsName(String methodName);

	List<PaymentMethod> findAllPaymentMethods();

	PaymentMethod findByMethodName(String paymentMethodName);

	double findFeesByPaymentMethodId(int paymentMethodId);

}
