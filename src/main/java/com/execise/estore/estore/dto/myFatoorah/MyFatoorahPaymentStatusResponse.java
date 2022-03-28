package com.execise.estore.estore.dto.myFatoorah;

import java.util.ArrayList;
import java.util.List;

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

//https://myfatoorah.readme.io/docs/get-payment-status
public class MyFatoorahPaymentStatusResponse {

	@JsonProperty("InvoiceId")
	private Long InvoiceId;
	@JsonProperty("InvoiceStatus")
	private String InvoiceStatus;
	@JsonProperty("InvoiceReference")
	private String InvoiceReference;
	@JsonProperty("CustomerReference")
	private String CustomerReference;
	@JsonProperty("CreatedDate")
	private String CreatedDate;
	@JsonProperty("ExpiryDate")
	private String ExpiryDate;
	@JsonProperty("InvoiceValue")
	private Long InvoiceValue;
	@JsonProperty("Comments")
	private String Comments;
	@JsonProperty("CustomerName")
	private String CustomerName;
	@JsonProperty("CustomerMobile")
	private String CustomerMobile;
	@JsonProperty("CustomerEmail")
	private String CustomerEmail;
	@JsonProperty("UserDefinedField")
	private String UserDefinedField;
	@JsonProperty("invoiceItems")
	private List<MyFatoorahInvoiceItemModel> invoiceItems = new ArrayList<>();
	@JsonProperty("invoiceTransactions")
	private List<MyFatoorahInvoiceTransactionModel > invoiceTransactions = new ArrayList<>();
}
