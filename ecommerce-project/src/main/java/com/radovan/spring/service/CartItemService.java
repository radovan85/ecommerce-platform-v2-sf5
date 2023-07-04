package com.radovan.spring.service;

import java.util.List;

import com.radovan.spring.dto.CartItemDto;

public interface CartItemService {

	CartItemDto addCartItem(Integer productId);
	
	void removeCartItem(Integer cartItemId);
	
	void removeAllCartItems();
	
	List<CartItemDto> listAllItems(Integer cartId);
	
	void removeAllByProductId(Integer productId);

	void removeAllByCartId(Integer cartId);
}
