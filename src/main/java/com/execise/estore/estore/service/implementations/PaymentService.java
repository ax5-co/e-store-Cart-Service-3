package com.execise.estore.estore.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.execise.estore.estore.dto.myFatoorah.MyFatoorahCustomerAddressModel;
import com.execise.estore.estore.dto.myFatoorah.MyFatoorahPaymentExecutionRequest;
import com.execise.estore.estore.entity.CustomerOrder;
import com.execise.estore.estore.entity.User;
import com.execise.estore.estore.enums.MyFatoorahApiType;
import com.execise.estore.estore.mapper.AddressEntityMyFatoorahMapper;
import com.execise.estore.estore.service.PaymentMethodService;
import com.execise.estore.estore.service.PaymentRecordService;
import com.execise.estore.estore.service.UserService;

@Service
@Transactional
public class PaymentService {

	@Autowired
	private PaymentMethodService paymentMethodService;

	@Autowired
	private UserService userService;
	@Autowired
	private AddressEntityMyFatoorahMapper addressEntityMyFatoorahMapper;
	
	@Autowired
	private PaymentRecordService paymentRecordService;
	
	public MyFatoorahPaymentExecutionRequest generateExecutePaymentRequest(
			CustomerOrder order, int paymentMethodId,
			MyFatoorahApiType apiType) {
		
		double paymentMethodFees = 
				paymentMethodService.findFeesByPaymentMethodId(paymentMethodId);
		
		
		double InvoiceValue = order.getOrderTotal().doubleValue() 
							+ paymentMethodFees;
		
		int PaymentMethodId = paymentMethodId;
		
		//Optional fields:
		
		User user = userService.getUser(order.getUser());
		
		String CustomerName = user.getFirstName() 
							+ user.getLastName();	
		
//		String CallBackUrl = "https://localhost:8080/api/payment/success";		//The return URL in case of a successful payment, refer to {https://myfatoorah.readme.io/docs/payment-inquiry} for more details.
		
//		String ErrorUrl = "https://localhost:8080/api/payment/error";		//The return URL in case of a failed payment or any exception raised during the payment, refer to {https://myfatoorah.readme.io/docs/payment-inquiry} for more details.
	
		String DisplayCurrencyIso = "KWD";		//The currency ISO code you want to displayed to the customer, by default it is the same as the base currency of the country API
		String MobileCountryCode = "965";		//Customer mobile number country code
		
		String CustomerMobile = ""+user.getPhoneNumber();		//String uses English letters ONLY and does not accept Arabic characters, Its length is between 0 and 11, Regular expression pattern is ^(?:(+)|(00)|(*)|())[0-9]{3,14}((#)|())$
		
		 String CustomerEmail = user.getEmail();		//Customer email
		
		String Language = "EN";			//"EN" OR "AR" to display the checkout page in English OR Arabic
		String CustomerReference = order.getNumber().toString();	//Refers to the order or transaction ID in your own system and you can use for payment inquiry as well
																//from this string back into uuid: UUID.fromString(CustomerReference);
		
		String CustomerCivilId = "";		//Your customer civil ID that you can associate with the transaction if needed
		String UserDefinedField = "";	//A custom field that you may use as additional information to be stored with the transaction
		MyFatoorahCustomerAddressModel[] CustomerAdress = new MyFatoorahCustomerAddressModel[1];
		CustomerAdress[0] = addressEntityMyFatoorahMapper.mapAddressEntityToCustomerAddressModel(order.getShippingAddress());
				
		String ExpiryDate = "";					//The date you want the payment to be expired, if not passed the default is considered from the account profile in the portal

		
		
		MyFatoorahPaymentExecutionRequest request = new MyFatoorahPaymentExecutionRequest();
		
		request.setInvoiceValue(InvoiceValue);
		request.setPaymentMethodId(PaymentMethodId);
		request.setCustomerName(CustomerName);
//		request.setCallBackUrl(CallBackUrl);
//		request.setErrorUrl(ErrorUrl);
		request.setDisplayCurrencyIso(DisplayCurrencyIso);
		request.setMobileCountryCode(MobileCountryCode);
		request.setCustomerMobile(CustomerMobile);
		request.setCustomerEmail(CustomerEmail);
		request.setLanguage(Language);
		request.setCustomerReference(CustomerReference);
		request.setUserDefinedField(UserDefinedField);
		request.setCustomerCivilId(CustomerCivilId);
		request.setCustomerAdress(CustomerAdress);
		request.setExpiryDate(ExpiryDate);
		
		request.setMyFatoorahApiType(apiType);
		
		return request;
	}

//	@Override
//	public Page<GetPaymentStatusResponse> getAllPaymentStatusForOrderNumber(UUID orderNumber, Pageable paging) {
//		
//		Page<PaymentRecord> page = paymentRecordService.findAllByCustomerReference(orderNumber.toString(), paging);
//		
//				
//				
//				// TODO Auto-generated method stub
//		return null;
//	}


}
