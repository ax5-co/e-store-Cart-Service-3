package com.execise.estore.estore.service.implementations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.execise.estore.estore.dto.AddressRequest;
import com.execise.estore.estore.dto.AddressResponse;
import com.execise.estore.estore.entity.Address;
import com.execise.estore.estore.entity.User;
import com.execise.estore.estore.exception.EStoreException;
import com.execise.estore.estore.mapper.AddressRequestResponseMapper;
import com.execise.estore.estore.repository.AddressRepository;
import com.execise.estore.estore.repository.UserRepository;
import com.execise.estore.estore.service.AddressService;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AddressRequestResponseMapper mapper;
	
	@Override
	public List<Address> getAllAddressesByUser(User user) {
		List<Address> userAddresses = addressRepository.findAllByUser(user);
		if(userAddresses.isEmpty())
			throw new EStoreException(
					"No addresses were found for this user -" 
					+ user.getUserName());
		return userAddresses;
		
	}

	@Override
	public AddressResponse saveAddress(User user, AddressRequest addressRequest) {
		Address address = mapper.mapRequestToAddress(addressRequest);
		address.setUser(user);
		addressRepository.save(address);
		user.getAddresses().add(address);
		userRepository.save(user);
		
		return mapper.mapAddressToResponse(address);
	}

	@Override
	public void removeByIdForUser(Long addressId, User user) {
		addressRepository.deleteByIdAndUser(addressId, user);
	}

	@Override
	public Address updateAddress(User user, AddressRequest addressRequest, Long addressId) {
		Address address = addressRepository.findByIdAndUser(addressId, user)
				.orElseThrow(
						() -> new EStoreException(
								"No address with id "+ addressId
								+"were found for this user -" 
								+ user.getUserName()));
		
		address.setTitle(addressRequest.getTitle());
		address.setGovernorate(addressRequest.getGovernorate());
		address.setArea(addressRequest.getArea());
		address.setBlock(addressRequest.getBlock());
		address.setStreet(addressRequest.getStreet());
		address.setBuilding(addressRequest.getBuilding());
		address.setFloor(addressRequest.getFloor());
		address.setApartment(addressRequest.getApartment());
		address.setNotes(addressRequest.getNotes());
		
		addressRepository.save(address);
		
		return address;
	}

	@Override
	public String getSummary(Long addressId) {
		
		Address address = addressRepository.findById(addressId).orElseThrow(
				() -> new EStoreException(
						"No address found with id "+ addressId));
		
		String summary = address.getTitle()+": "
				+address.getGovernorate()+", "
				+address.getArea()+", "
				+"block "+address.getBlock()+", "
				+"St. "+address.getStreet()+", "
				+"Bldg. "+address.getBuilding()
				+"floor "+address.getFloor()
				+"Apt. no. "+address.getApartment();
		
		return summary;
		
	}

	@Override
	public Address getAddressByIdAndUser(Long addressId, User user) {
		return addressRepository.findByIdAndUser(addressId, user).orElseThrow(
				() -> new EStoreException(
						"No address found with id "+ addressId +
						" is found for user - "+ user.getUserName()));
	}

	@Override
	public List<AddressResponse> getAllAddressesResponses(List<Address> userAddresses) {
		
		List<AddressResponse> addressResponses = new ArrayList<>();
		for (Address address : userAddresses) {
			addressResponses.add(mapper.mapAddressToResponse(address));
		}
		return addressResponses;
	}

	@Override
	public AddressResponse getAddressResponse(Address address) {
		
		return mapper.mapAddressToResponse(address);
	}
}
