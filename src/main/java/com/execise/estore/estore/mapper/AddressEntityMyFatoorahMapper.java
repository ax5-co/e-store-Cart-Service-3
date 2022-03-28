package com.execise.estore.estore.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.execise.estore.estore.dto.myFatoorah.MyFatoorahCustomerAddressModel;
import com.execise.estore.estore.entity.Address;
import com.execise.estore.estore.service.AddressService;

@Component
public class AddressEntityMyFatoorahMapper {

	@Autowired
	private AddressService addressService;
	
	public MyFatoorahCustomerAddressModel mapAddressEntityToCustomerAddressModel(Address addressEntity) {
		
		MyFatoorahCustomerAddressModel customerAddressModel = new MyFatoorahCustomerAddressModel();
		customerAddressModel.setAddress(addressService.getSummary(addressEntity.getId()));
		customerAddressModel.setAddressInstructions(addressEntity.getNotes());
		customerAddressModel.setBlock("Block "+addressEntity.getBlock());
		customerAddressModel.setHouseBuildingNo(addressEntity.getBuilding());
		customerAddressModel.setStreet(addressEntity.getStreet());
		
		return customerAddressModel;
	}

}
