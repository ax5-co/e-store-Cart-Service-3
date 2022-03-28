package com.execise.estore.estore.mapper;

import org.springframework.stereotype.Component;

import com.execise.estore.estore.dto.AddressRequest;
import com.execise.estore.estore.dto.AddressResponse;
import com.execise.estore.estore.entity.Address;

@Component
public class AddressRequestResponseMapper {
	

	public Address mapRequestToAddress(AddressRequest addressRequest) {

		Address address = new Address();
		
		
		
		address.setTitle(addressRequest.getTitle());
		address.setGovernorate(addressRequest.getGovernorate());
		address.setArea(addressRequest.getArea());
		address.setBlock(addressRequest.getBlock());
		address.setStreet(addressRequest.getStreet());
		address.setBuilding(addressRequest.getBuilding());
		address.setFloor(addressRequest.getFloor());
		address.setApartment(addressRequest.getApartment());
		address.setNotes(addressRequest.getNotes());
		
		return address;
		
	}
	
	public AddressResponse mapAddressToResponse(Address address) {

		AddressResponse response = new AddressResponse();
		
		response.setUsername(address.getUser().getUserName());
		response.setAddressId(address.getId());
		response.setTitle(address.getTitle());
		response.setGovernorate(address.getGovernorate());
		response.setArea(address.getArea());
		response.setBlock(address.getBlock());
		response.setStreet(address.getStreet());
		response.setBuilding(address.getBuilding());
		response.setFloor(address.getFloor());
		response.setApartment(address.getApartment());
		response.setNotes(address.getNotes());
		
		return response;
		
	}

}
