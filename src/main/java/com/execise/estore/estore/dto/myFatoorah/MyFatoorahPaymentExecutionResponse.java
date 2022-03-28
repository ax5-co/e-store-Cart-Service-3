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

//https://myfatoorah.readme.io/docs/execute-payment
public class MyFatoorahPaymentExecutionResponse extends PaymentModel{

	@JsonProperty("IsSuccess")
	private boolean IsSuccess;
	
	@JsonProperty("Message")
	private String Message;
	
	@JsonProperty("ValidationErrors")
	private String ValidationErrors;
	
	@JsonProperty("Data")
	private MyFatoorahPaymentExecutionResponseData Data;
}
