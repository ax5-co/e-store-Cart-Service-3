package com.execise.estore.estore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.execise.estore.estore.entity.PaymentRecord;

@Repository
public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, Long>{

	Page<PaymentRecord> findAllByCustomerReference(String string, Pageable paging);

}
