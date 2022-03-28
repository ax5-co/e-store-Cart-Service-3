package com.execise.estore.estore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.execise.estore.estore.dto.AddressRequest;
import com.execise.estore.estore.dto.AddressResponse;
import com.execise.estore.estore.entity.Address;
import com.execise.estore.estore.service.AddressService;
import com.execise.estore.estore.service.implementations.AuthService;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

	@Autowired
	private AddressService addressService;

	@Autowired
	private AuthService authService;

	@GetMapping("/")
	public ResponseEntity<List<AddressResponse>> getAllAddressesForLoggedInUser() {
		List<Address> userAddresses = addressService.getAllAddressesByUser(authService.getCurrentUser());
		
		List<AddressResponse> addressResponses = addressService.getAllAddressesResponses(userAddresses);
		
		return new ResponseEntity<>(addressResponses, HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<AddressResponse> saveNewAddressForLoggedInUser(@RequestBody AddressRequest addressRequest) {
		AddressResponse newAddressResponse = addressService.saveAddress(authService.getCurrentUser(), addressRequest);
		return new ResponseEntity<>(newAddressResponse, HttpStatus.CREATED);
	}

	@DeleteMapping("/{addressId}")
	public ResponseEntity<Object> deleteAddressForLoggedInUser(@PathVariable Long addressId) {
		addressService.removeByIdForUser(addressId, authService.getCurrentUser());
		return new ResponseEntity<>("Specified Address is Removed", HttpStatus.OK);
	}

	@PutMapping("/{addressId}")
	public ResponseEntity<AddressResponse> updateAddressForLoggedInUser(@PathVariable Long addressId,
			@RequestBody AddressRequest addressRequest) {
		Address updatedAddress = addressService.updateAddress(authService.getCurrentUser(), addressRequest, addressId);
		AddressResponse addressResponse = addressService.getAddressResponse(updatedAddress);
		return new ResponseEntity<>(addressResponse, HttpStatus.ACCEPTED);
	}
}
