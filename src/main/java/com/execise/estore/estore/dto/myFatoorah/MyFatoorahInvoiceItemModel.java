package com.execise.estore.estore.dto.myFatoorah;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
//https://myfatoorah.readme.io/docs/get-payment-status#invoiceitemmodel
public class MyFatoorahInvoiceItemModel {

	@JsonProperty("ItemName")
	private String ItemName;
	@JsonProperty("Quantity")
	private int Quantity;
	@JsonProperty("UnitPrice")
	private double UnitPrice;
	@JsonProperty("Weight")
	private float Weight;
	@JsonProperty("Width")
	private float Width;
	@JsonProperty("Height")
	private float Height;
	@JsonProperty("Depth")
	private float Depth;
}
