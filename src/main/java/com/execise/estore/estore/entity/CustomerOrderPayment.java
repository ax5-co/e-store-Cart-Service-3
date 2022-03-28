package com.execise.estore.estore.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.execise.estore.estore.dto.myFatoorah.MyFatoorahInvoiceItemModel;
import com.execise.estore.estore.dto.myFatoorah.MyFatoorahInvoiceTransactionModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerOrderPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private UUID orderNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)   //uni-directional
	@JoinColumn(name = "fk_CustomerOrder", referencedColumnName = "cart_id")
	private CustomerOrder customerOrder; //duplication of data just to keep reads tolerable
	
	private int PaymentMethodId;
	
	private Long InvoiceId;
	private String InvoiceStatus;
	private String InvoiceReference;
	private String CustomerReference;
	private String CreatedDate;
	private String ExpiryDate;
	
	private String Comments;
	private String CustomerName;
	private String CustomerMobile;
	private String CustomerEmail;
	private String UserDefinedField;
	
//	private List<InvoiceItemModel> invoiceItems = new ArrayList<>();
//	private List<InvoiceTransactionModel > invoiceTransactions = new ArrayList<>();
}
