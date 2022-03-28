package com.execise.estore.estore.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemPreview {

	private String productName;
	
	private String productColor;
	
	private String productVariantSize; 
	
	private BigDecimal productUnitPrice; 

	private int quantity;
	
	private BigDecimal itemTotal;
	
	@CreatedDate
	private Date createdAt;
	
	
	private boolean inStock = true;

}
