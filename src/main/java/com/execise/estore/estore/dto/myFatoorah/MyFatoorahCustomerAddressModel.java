package com.execise.estore.estore.dto.myFatoorah;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class MyFatoorahCustomerAddressModel {

	@JsonProperty("Block")
	private String Block;
	@JsonProperty("Street")
	private String Street ;
	@JsonProperty("HouseBuildingNo")
	private String HouseBuildingNo ;
	@JsonProperty("Address")
	private String Address ;
	@JsonProperty("AddressInstructions")
	private String AddressInstructions ;
	
}
