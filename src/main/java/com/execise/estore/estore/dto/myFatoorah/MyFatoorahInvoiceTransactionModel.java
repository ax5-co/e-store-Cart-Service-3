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
//https://myfatoorah.readme.io/docs/get-payment-status#invoicetransactionmodel
public class MyFatoorahInvoiceTransactionModel {

	@JsonProperty("TransactionDate")
	private String TransactionDate;
	@JsonProperty("PaymentGateway")
	private String PaymentGateway;
	@JsonProperty("ReferenceId")
	private String ReferenceId;
	@JsonProperty("TrackId")
	private String TrackId;
	@JsonProperty("TransactionId")
	private String TransactionId;
	@JsonProperty("PaymentId")
	private String PaymentId;
	@JsonProperty("AuthorizationId")
	private String AuthorizationId;
	@JsonProperty("TransactionStatus")
	private String TransactionStatus;
	@JsonProperty("TransationValue")
	private String TransationValue;
	@JsonProperty("CustomerServiceCharge")
	private String CustomerServiceCharge;
	@JsonProperty("DueValue")
	private String DueValue;
	@JsonProperty("PaidCurrency")
	private String PaidCurrency;
	@JsonProperty("PaidCurrencyValue")
	private String PaidCurrencyValue;
	@JsonProperty("Currency")
	private String Currency;
	@JsonProperty("Error")
	private String Error;
	@JsonProperty("ErrorCode")
	private String ErrorCode;
}
