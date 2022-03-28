package com.execise.estore.estore.service.implementations;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.execise.estore.estore.dto.myFatoorah.MyFatoorahPaymentExecutionResponse;
import com.execise.estore.estore.entity.CustomerOrder;
import com.execise.estore.estore.entity.PaymentRecord;
import com.execise.estore.estore.repository.PaymentRecordRepository;
import com.execise.estore.estore.service.PaymentRecordService;

@Service
@Transactional
public class PaymentRecordServiceImpl implements PaymentRecordService {

	@Autowired
	private PaymentRecordRepository paymentRecordRepository;
	
	@Override
	public PaymentRecord savePaymentRecord(MyFatoorahPaymentExecutionResponse responseBody, CustomerOrder order) {
		
		Long orderId = order.getId();
		Long userId = order.getUser().getId();
//		UUID orderNumber = order.getNumber();
		
		PaymentRecord paymentRecord = new PaymentRecord();

		paymentRecord.setIsSuccess(responseBody.isIsSuccess());
		paymentRecord.setMessage(responseBody.getMessage());
		paymentRecord.setValidationErrors(responseBody.getValidationErrors());
		paymentRecord.setInvoiceId(responseBody.getData().getInvoiceId());
		paymentRecord.setIsDirectPayment(responseBody.getData().isIsDirectPayment());
		paymentRecord.setPaymentURL(responseBody.getData().getPaymentURL());
		paymentRecord.setCustomerReference(responseBody.getData().getCustomerReference());
		paymentRecord.setUserDefinedField(responseBody.getData().getUserDefinedField());
		paymentRecord.setOrderId(orderId);
		paymentRecord.setUserId(userId);
	
		paymentRecordRepository.save(paymentRecord);
		
		return paymentRecord;
	}

	@Override
	public Page<PaymentRecord> getAllByCustomerReference(String string, Pageable paging) {
		return paymentRecordRepository.findAllByCustomerReference(string, paging);
	}

}
