package com.radovan.spring.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.dto.CustomerDto;
import com.radovan.spring.dto.ShippingAddressDto;
import com.radovan.spring.entity.ShippingAddressEntity;
import com.radovan.spring.repository.ShippingAddressRepository;
import com.radovan.spring.service.CustomerService;
import com.radovan.spring.service.ShippingAddressService;

@Service
@Transactional
public class ShippingAddressServiceImpl implements ShippingAddressService {

	@Autowired
	private ShippingAddressRepository shippingAddressRepository;

	@Autowired
	private TempConverter tempConverter;

	@Autowired
	private CustomerService customerService;

	@Override
	public ShippingAddressDto retrieveShippingAddress() {
		// TODO Auto-generated method stub
		ShippingAddressDto returnValue = null;
		CustomerDto customer = customerService.getCurrentCustomer();
		Optional<ShippingAddressEntity> addressOpt = shippingAddressRepository
				.findById(customer.getShippingAddressId());
		if (addressOpt.isPresent()) {
			returnValue = tempConverter.shippingAddressEntityToDto(addressOpt.get());
		}

		return returnValue;
	}

	@Override
	public ShippingAddressDto updateShippingAddress(Integer addressId, ShippingAddressDto address) {
		// TODO Auto-generated method stub
		ShippingAddressDto returnValue = null;
		Optional<ShippingAddressEntity> addressOpt = shippingAddressRepository.findById(addressId);
		if (addressOpt.isPresent()) {
			address.setShippingAddressId(addressId);
			ShippingAddressEntity addressEntity = tempConverter.shippingAddressDtoToEntity(address);
			ShippingAddressEntity updatedAddress = shippingAddressRepository.saveAndFlush(addressEntity);
			returnValue = tempConverter.shippingAddressEntityToDto(updatedAddress);
		}

		return returnValue;
	}

	@Override
	public ShippingAddressDto getShippingAddress(Integer shippingAddressId) {
		// TODO Auto-generated method stub
		ShippingAddressDto returnValue = null;
		Optional<ShippingAddressEntity> addressOpt = shippingAddressRepository.findById(shippingAddressId);
		if (addressOpt.isPresent()) {
			returnValue = tempConverter.shippingAddressEntityToDto(addressOpt.get());
		}
		return returnValue;
	}

	@Override
	public ShippingAddressDto addShippingAddress(ShippingAddressDto shippingAddress) {
		// TODO Auto-generated method stub
		ShippingAddressEntity addressEntity = tempConverter.shippingAddressDtoToEntity(shippingAddress);
		ShippingAddressEntity storedAddress = shippingAddressRepository.save(addressEntity);
		ShippingAddressDto returnValue = tempConverter.shippingAddressEntityToDto(storedAddress);
		return returnValue;
	}

}
