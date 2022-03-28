package com.execise.estore.estore.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.execise.estore.estore.entity.CustomerOrder;
import com.execise.estore.estore.entity.User;

@Repository
public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {

	Optional<CustomerOrder> findByNumberAndUser(UUID orderUUIDNumber, User user);

	List<CustomerOrder> findAllByUser(User user);

	List<CustomerOrder> findTop20ByUser(User user);

	Optional<CustomerOrder> findByNumber(UUID orderNumber);

}
