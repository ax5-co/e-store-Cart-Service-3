package com.execise.estore.estore.dto.myFatoorah;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MyFatoorahPaymentExecutionResponseData {

	@JsonProperty("InvoiceId")
	private Long InvoiceId;
	
	@JsonProperty("IsDirectPayment")
	private boolean IsDirectPayment;
	
	@JsonProperty("PaymentURL")
	private String PaymentURL;		//we redirect the customer to this url to actually pay. 
	
	@JsonProperty("CustomerReference")
	private String CustomerReference;
	
	@JsonProperty("UserDefinedField")
	private String UserDefinedField;
	
	//only if the RecurringModel is used in Request
	@JsonProperty("RecurringId")
	private String RecurringId;
	
}
