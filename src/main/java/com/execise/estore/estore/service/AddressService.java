package com.execise.estore.estore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.execise.estore.estore.dto.AddressRequest;
import com.execise.estore.estore.dto.AddressResponse;
import com.execise.estore.estore.entity.Address;
import com.execise.estore.estore.entity.User;

@Service
public interface AddressService {

	List<Address> getAllAddressesByUser(User currentUser);

	AddressResponse saveAddress(User currentUser, AddressRequest addressRequest);

	void removeByIdForUser(Long addressId, User currentUser);

	Address updateAddress(User currentUser, AddressRequest addressRequest, Long addressId);

	String getSummary(Long addressId);

	Address getAddressByIdAndUser(Long addressId, User user);

	List<AddressResponse> getAllAddressesResponses(List<Address> userAddresses);

	AddressResponse getAddressResponse(Address address);

}
