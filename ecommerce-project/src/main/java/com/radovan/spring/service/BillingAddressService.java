package com.radovan.spring.service;

import com.radovan.spring.dto.BillingAddressDto;

public interface BillingAddressService {

	BillingAddressDto retrieveBillingAddress();

	BillingAddressDto updateBillingAddress(Integer addressId, BillingAddressDto address);

	BillingAddressDto getBillingAddress(Integer billingAddressId);

	BillingAddressDto addBillingAddress(BillingAddressDto billingAddress);
}
