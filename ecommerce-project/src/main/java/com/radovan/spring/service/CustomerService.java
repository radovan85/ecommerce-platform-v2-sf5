package com.radovan.spring.service;

import java.util.List;

import com.radovan.spring.dto.CustomerDto;
import com.radovan.spring.form.RegistrationForm;

public interface CustomerService {

	CustomerDto storeCustomer(RegistrationForm form);

	List<CustomerDto> listAll();

	CustomerDto getCustomerById(Integer customerId);

	CustomerDto getCustomerByUserId(Integer userId);

	void deleteCustomer(Integer customerId);

	CustomerDto getCurrentCustomer();

	CustomerDto updateCustomer(Integer customerId, CustomerDto customer);

	CustomerDto addCustomer(CustomerDto customer);
}
