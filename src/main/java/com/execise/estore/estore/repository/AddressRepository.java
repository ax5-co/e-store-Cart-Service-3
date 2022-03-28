package com.execise.estore.estore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.execise.estore.estore.entity.Address;
import com.execise.estore.estore.entity.User;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

	List<Address> findAllByUser(User user);

	void deleteByIdAndUser(Long addressId, User user);

	Optional<Address> findByIdAndUser(Long addressId, User user);

}
