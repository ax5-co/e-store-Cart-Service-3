package com.execise.estore.estore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequest {

	private String productName;
	
	private String productColor;
	
	private String productVariantSize;
	
	private int quantity;
	
	
}
