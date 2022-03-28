package com.execise.estore.estore.enums;

import lombok.Getter;

@Getter
public enum MyFatoorahApiType {

	INITIATE("https://apitest.myfatoorah.com/v2/initiatePayment"),
	EXECUTE("https://apitest.myfatoorah.com/v2/ExecutePayment"),
	PAYMENT_INQUIRY("//https://myfatoorah.readme.io/docs/get-payment-status");
	
	private String URL;
	
	private MyFatoorahApiType(String url) {
		this.URL = url;
	}
}
