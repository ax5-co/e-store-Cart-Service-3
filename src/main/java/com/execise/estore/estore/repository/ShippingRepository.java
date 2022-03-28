package com.execise.estore.estore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.execise.estore.estore.entity.Shipping;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Long> {

	Optional<Shipping> findByShippingProvider(String shippingProvider);
}
