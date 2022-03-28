package com.execise.estore.estore.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Shipping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String shippingProvider;
	
	private BigDecimal shippingFees;
	
	//typically, there should be a boolean isDefault to have a meaning
	//for the DefaultShipping in shippingService
	//But, for now, let's just use the id(0) as default shipping.
}
