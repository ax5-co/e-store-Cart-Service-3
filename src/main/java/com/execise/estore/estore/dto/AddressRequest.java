package com.execise.estore.estore.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {

	@NotNull
	@Size(min = 1, max = 50)
	private String title;

	@NotNull
	@Size(min = 1, max = 15)	
	private String governorate;

	@NotNull
	@Size(min = 1, max = 15)	
	private String area;
	
	private int block;
	
	private String street;
	
	private String building;
	
	private int floor;
	
	private int apartment;
	
	private String notes;
	
}
