package com.execise.estore.estore.mapper;

import org.springframework.stereotype.Component;

import com.execise.estore.estore.dto.myFatoorah.MyFatoorahPaymentExecutionResponse;
import com.execise.estore.estore.dto.myFatoorah.MyFatoorahPaymentExecutionResponseData;
import com.execise.estore.estore.exception.EStoreException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MyFatoorahDtoMappers {

	public MyFatoorahPaymentExecutionResponse mapStringToExecutePaymentResponse(String body)  {
		
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			boolean IsSuccess = objectMapper.readTree(body).findValue("IsSuccess").asBoolean();
			
			
			String Message = objectMapper.readTree(body).findValue("Message").asText();
			String ValidationErrors = objectMapper.readTree(body).findValue("ValidationErrors").asText();
			
			System.out.println("\n\n IsSuccess = " + IsSuccess);
			System.out.println("\n\n Message = " + Message);
			System.out.println("\n\n ValidationErrors = " + ValidationErrors);
		
			
			MyFatoorahPaymentExecutionResponseData Data = new MyFatoorahPaymentExecutionResponseData();
			
			Data.setInvoiceId(objectMapper.readTree(body).findValue("InvoiceId").asLong());
			Data.setIsDirectPayment(objectMapper.readTree(body).findValue("IsDirectPayment").asBoolean());
			Data.setPaymentURL(objectMapper.readTree(body).findValue("PaymentURL").asText());
			Data.setCustomerReference(objectMapper.readTree(body).findValue("CustomerReference").asText());
			Data.setUserDefinedField(objectMapper.readTree(body).findValue("UserDefinedField").asText());
			Data.setRecurringId(objectMapper.readTree(body).findValue("RecurringId").asText());

			System.out.println("\n\n PaymentURL = " + ValidationErrors);
			
			MyFatoorahPaymentExecutionResponse  executePaymentResponse= new MyFatoorahPaymentExecutionResponse();
			executePaymentResponse.setData(Data);
			executePaymentResponse.setIsSuccess(IsSuccess);
			executePaymentResponse.setMessage(Message);
			executePaymentResponse.setValidationErrors(ValidationErrors);
			
			return executePaymentResponse;
			
		} catch (Exception e) {
			throw new EStoreException("Could not map MyFatoorah Api response of ExecutePayment to an ExecutePymentResponse dto");
		}

	}

}
