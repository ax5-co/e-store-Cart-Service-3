package com.execise.estore.estore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.execise.estore.estore.dto.myFatoorah.MyFatoorahPaymentExecutionResponse;
import com.execise.estore.estore.entity.CustomerOrder;
import com.execise.estore.estore.entity.PaymentRecord;

@Service
public interface PaymentRecordService {

	PaymentRecord savePaymentRecord(MyFatoorahPaymentExecutionResponse responseBody, CustomerOrder order);

	Page<PaymentRecord> getAllByCustomerReference(String string, Pageable paging);

}
