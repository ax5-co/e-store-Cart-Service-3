package com.execise.estore.estore.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

	private UUID orderNumber;
	
	private Date createdAt;
	private BigDecimal cartTotal;
	private BigDecimal shippingTotal;
	private BigDecimal orderTotal;

	private String shippingAddressSummary;
	
	private String paymentMethodName;
	
	private Set<OrderItemPreview> orderItems;
	

	private String username;
	
}
