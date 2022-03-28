package com.execise.estore.estore.service.implementations;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.execise.estore.estore.EStoreApplication;
import com.execise.estore.estore.entity.Shipping;
import com.execise.estore.estore.exception.EStoreException;
import com.execise.estore.estore.repository.ShippingRepository;
import com.execise.estore.estore.service.ShippingService;

@Service
public class ShippingServiceImpl implements ShippingService {

	@Autowired
	private ShippingRepository shippingRepository;
	
	@Override
	public Shipping getDefaultShipping() {
		return shippingRepository.findById(1L).orElseThrow(
				()-> new EStoreException("No shipping record with "
						+ "id - 1 ... no default shipping!")
				);
	}

	@Override
	public BigDecimal getShippingFeesForDefaultShipping() {
		
		return getDefaultShipping().getShippingFees();
	}

	@Override
	public String getShippingProviderForDefaultShipping() {
		return getDefaultShipping().getShippingProvider();
	}

	@Override
	public BigDecimal getShippingFeesForShippingProvider(String shippingProvider) {

		Shipping shipping = shippingRepository.findByShippingProvider(
				shippingProvider).orElseThrow(
				()-> new EStoreException("No shipping record with "
						+ "provider - " + shippingProvider)
				);
		return shipping.getShippingFees();
	}

}
