package com.execise.estore.estore.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.execise.estore.estore.entity.Shipping;

@Service
public interface ShippingService {

	Shipping getDefaultShipping();
	BigDecimal getShippingFeesForDefaultShipping();
	String getShippingProviderForDefaultShipping();
	
	BigDecimal getShippingFeesForShippingProvider(String shippingProvider);
}
