package com.radovan.spring.service;

import java.util.List;

import com.radovan.spring.dto.ProductDto;

public interface ProductService {

	List<ProductDto> listAll();

	void deleteProduct(Integer productId);

	ProductDto addProduct(ProductDto product);

	List<ProductDto> listAllByKeyword(String keyword);

	ProductDto getProductById(Integer productId);

	ProductDto updateProduct(Integer productId, ProductDto product);
}
