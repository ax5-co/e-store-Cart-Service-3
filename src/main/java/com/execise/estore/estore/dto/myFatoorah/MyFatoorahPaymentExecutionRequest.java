package com.execise.estore.estore.dto.myFatoorah;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

//https://myfatoorah.readme.io/docs/execute-payment
public class MyFatoorahPaymentExecutionRequest extends PaymentModel {
	
	@JsonProperty("")
	private double InvoiceValue;
	@JsonProperty("")
	private int PaymentMethodId;
	
	//Optional fields:
	@JsonProperty("CustomerName")
	private String CustomerName = "";	//The return URL in case of a successful payment, refer to Payment Inquiry for more details.

//	@JsonProperty("CallBackUrl")
//	private String CallBackUrl = "https://localhost:8080/api/payment/success";		//The return URL in case of a successful payment, refer to {https://myfatoorah.readme.io/docs/payment-inquiry} for more details.
//	@JsonProperty("ErrorUrl")
//	private String ErrorUrl = "https://localhost:8080/api/payment/error";		//The return URL in case of a failed payment or any exception raised during the payment, refer to {https://myfatoorah.readme.io/docs/payment-inquiry} for more details.
	@JsonProperty("DisplayCurrencyIso")
	private String DisplayCurrencyIso = "KWD";		//The currency ISO code you want to displayed to the customer, by default it is the same as the base currency of the country API
	@JsonProperty("MobileCountryCode")
	private String MobileCountryCode = "965";		//Customer mobile number country code
	@JsonProperty("CustomerMobile")
	private String CustomerMobile = "";		//String uses English letters ONLY and does not accept Arabic characters, Its length is between 0 and 11, Regular expression pattern is ^(?:(+)|(00)|(*)|())[0-9]{3,14}((#)|())$
	@JsonProperty("CustomerEmail")
	private String CustomerEmail = "";		//Customer email
	@JsonProperty("Language")
	private String Language = "EN";			//"EN" OR "AR" to display the checkout page in English OR Arabic
	@JsonProperty("CustomerReference")
	private String CustomerReference = "";	//Refers to the order or transaction ID in your own system and you can use for payment inquiry as well
	@JsonProperty("CustomerCivilId")
	private String CustomerCivilId = "";		//Your customer civil ID that you can associate with the transaction if needed
	@JsonProperty("UserDefinedField")
	private String UserDefinedField = "";	//A custom field that you may use as additional information to be stored with the transaction
	@JsonProperty("CustomerAdress")
	private MyFatoorahCustomerAddressModel[] CustomerAdress = new MyFatoorahCustomerAddressModel[1];
	@JsonProperty("ExpiryDate")
	private String ExpiryDate = "";					//The date you want the payment to be expired, if not passed the default is considered from the account profile in the portal
}
