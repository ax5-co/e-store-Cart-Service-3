package com.execise.estore.estore.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {

	private String productName;
	private String productColor;
	private String prodictvariantSize;
	private int quantity;
	private BigDecimal unitPrice;
	private UUID uuid;
	private boolean isAvailable;
	
}
