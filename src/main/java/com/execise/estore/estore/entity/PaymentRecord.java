package com.execise.estore.estore.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.execise.estore.estore.dto.myFatoorah.MyFatoorahPaymentExecutionResponseData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private boolean IsSuccess;
	private String Message;
	private String ValidationErrors;
	
	private Long InvoiceId;
	private boolean IsDirectPayment;
	private String PaymentURL;		//we redirect the customer to this url to actually pay.
	
	private String CustomerReference; //this is the Order.java > number (UUID)
	private String UserDefinedField;
	
	private Long orderId;
	private Long userId;
	@Override
	public String toString() {
		return "Payment [id=" + id + ", IsSuccess=" + IsSuccess + ", Message=" + Message + ", ValidationErrors="
				+ ValidationErrors + ", InvoiceId=" + InvoiceId + ", IsDirectPayment=" + IsDirectPayment
				+ ", PaymentURL=" + PaymentURL + ", CustomerReference=" + CustomerReference + ", UserDefinedField="
				+ UserDefinedField + ", orderId=" + orderId + ", userId=" + userId + "]";
	}
	
	
}
