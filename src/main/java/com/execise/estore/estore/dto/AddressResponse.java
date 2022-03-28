package com.execise.estore.estore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {

	private String username;
	
	private Long addressId;
	
	private String title;
	
	private String governorate;

	private String area;
	
	private int block;
	
	private String street;
	
	private String building;
	
	private int floor;
	
	private int apartment;
	
	private String notes;
	
}