package com.radovan.spring.service;

import com.radovan.spring.dto.ShippingAddressDto;

public interface ShippingAddressService {

	ShippingAddressDto updateShippingAddress(Integer addressId, ShippingAddressDto address);

	ShippingAddressDto retrieveShippingAddress();

	ShippingAddressDto getShippingAddress(Integer shippingAddressId);

	ShippingAddressDto addShippingAddress(ShippingAddressDto shippingAddress);

}
