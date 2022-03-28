package com.execise.estore.estore.service.implementations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.execise.estore.estore.entity.PaymentMethod;
import com.execise.estore.estore.exception.EStoreException;
import com.execise.estore.estore.repository.PaymentMethodRepository;
import com.execise.estore.estore.service.PaymentMethodService;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

	@Autowired
	private PaymentMethodRepository paymentMethodRepository;

	@Override
	public List<String> findAllPaymentMethodsNames() {
		List<PaymentMethod> methods = paymentMethodRepository.findAll();
		List<String> methodNames = new ArrayList<String>();
		for (PaymentMethod method : methods) {
			methodNames.add(method.getMethodName());
		}
		if (methodNames.isEmpty())
			throw new EStoreException("No Payment Methods' Names"
					+ " are available!");
		else
			return methodNames;
	}

	@Override
	public BigDecimal findFeesByMethodsName(String methodName) {
		
		PaymentMethod method = paymentMethodRepository
				.findByMethodNameEquals(methodName).orElseThrow(
				() -> new EStoreException("This Payment Method Name was"
									+ " not found (check EXACT spelling)!"));
		return method.getMethodFees();
	}

	@Override
	public List<PaymentMethod> findAllPaymentMethods() {
		List<PaymentMethod> methods = paymentMethodRepository.findAll();

		if (methods.isEmpty())
			throw new EStoreException("No Payment Methods are available!");
		else
			return methods;
	}

	@Override
	public PaymentMethod findByMethodName(String paymentMethodName) {
		return paymentMethodRepository.findByMethodNameEquals(paymentMethodName)
				.orElseThrow(
						() -> new EStoreException(
								"This Payment Method Name was"
								+ " not found (check EXACT spelling)!"));
	}

	@Override
	public double findFeesByPaymentMethodId(int paymentMethodId) {
		PaymentMethod method = paymentMethodRepository.findById(
				(long) paymentMethodId).orElseThrow(
				() -> new EStoreException(
						"Could not find PaymentMethod with Id - " 
				+ paymentMethodId))
				;
		return method.getMethodFees().doubleValue();
	}

}
