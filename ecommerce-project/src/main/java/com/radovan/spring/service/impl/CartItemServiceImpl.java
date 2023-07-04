package com.radovan.spring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.dto.CartDto;
import com.radovan.spring.dto.CartItemDto;
import com.radovan.spring.dto.CustomerDto;
import com.radovan.spring.entity.CartEntity;
import com.radovan.spring.entity.CartItemEntity;
import com.radovan.spring.entity.CustomerEntity;
import com.radovan.spring.entity.ProductEntity;
import com.radovan.spring.repository.CartItemRepository;
import com.radovan.spring.repository.CartRepository;
import com.radovan.spring.repository.ProductRepository;
import com.radovan.spring.service.CartItemService;
import com.radovan.spring.service.CartService;
import com.radovan.spring.service.CustomerService;

@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private CartService cartService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private TempConverter tempConverter;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CartRepository cartRepository;

	@Override
	public CartItemDto addCartItem(Integer productId) {
		// TODO Auto-generated method stub
		CartItemDto returnValue = null;
		CartItemDto cartItem = new CartItemDto();
		CustomerDto customer = customerService.getCurrentCustomer();
		CustomerEntity customerEntity = tempConverter.customerDtoToEntity(customer);
		Optional<ProductEntity> productOpt = productRepository.findById(productId);
		if (productOpt.isPresent()) {
			ProductEntity productEntity = productOpt.get();
			cartItem.setProductId(productEntity.getProductId());
			CartEntity cartEntity = customerEntity.getCart();
			cartItem.setCartId(cartEntity.getCartId());
			List<CartItemEntity> allCartItems = cartEntity.getCartItems();

			for (CartItemEntity tempItem : allCartItems) {
				if (tempItem.getProduct().getProductId().intValue() == productId) {
					cartItem.setCartItemId(tempItem.getCartItemId());
					cartItem.setQuantity(tempItem.getQuantity() + 1);
					cartItem.setPrice(tempItem.getPrice() + productEntity.getProductPrice());
					CartItemEntity cartItemEntity = tempConverter.cartItemDtoToEntity(cartItem);
					CartItemEntity storedItem = cartItemRepository.save(cartItemEntity);
					cartService.refreshCartState(cartEntity.getCartId());
					returnValue = tempConverter.cartItemEntityToDto(storedItem);
					return returnValue;
				}
			}

			cartItem.setQuantity(1);
			cartItem.setPrice(productEntity.getProductPrice());
			CartItemEntity cartItemEntity = tempConverter.cartItemDtoToEntity(cartItem);
			CartItemEntity storedItem = cartItemRepository.save(cartItemEntity);
			cartService.refreshCartState(cartEntity.getCartId());
			returnValue = tempConverter.cartItemEntityToDto(storedItem);
		}

		return returnValue;
	}

	@Override
	public void removeCartItem(Integer cartItemId) {
		// TODO Auto-generated method stub
		Optional<CartItemEntity> cartItemOpt = cartItemRepository.findById(cartItemId);
		if (cartItemOpt.isPresent()) {
			Integer cartId = cartItemOpt.get().getCart().getCartId();
			cartItemRepository.removeItem(cartItemId);
			cartItemRepository.flush();
			cartService.refreshCartState(cartId);
		}
	}

	@Override
	public void removeAllCartItems() {
		// TODO Auto-generated method stub
		CustomerDto customer = customerService.getCurrentCustomer();
		Integer cartId = customer.getCartId();
		cartItemRepository.removeAllItems(cartId);
		cartItemRepository.flush();
		cartService.refreshCartState(cartId);
	}

	@Override
	public List<CartItemDto> listAllItems(Integer cartId) {
		// TODO Auto-generated method stub
		List<CartItemDto> returnValue = new ArrayList<CartItemDto>();
		CartDto cart = cartService.getMyCart();
		Optional<List<CartItemEntity>> allItemsOpt = Optional
				.ofNullable(cartItemRepository.findAllByCartId(cart.getCartId()));
		if (!allItemsOpt.isEmpty()) {
			allItemsOpt.get().forEach((cartItem) -> {
				CartItemDto cartItemDto = tempConverter.cartItemEntityToDto(cartItem);
				returnValue.add(cartItemDto);
			});
		}

		return returnValue;
	}

	@Override
	public void removeAllByProductId(Integer productId) {
		// TODO Auto-generated method stub
		cartItemRepository.removeAllByProductId(productId);
		cartItemRepository.flush();

		List<CartEntity> allCarts = cartRepository.findAll();
		if (!allCarts.isEmpty()) {
			allCarts.forEach((cartEntity) -> {
				cartService.refreshCartState(cartEntity.getCartId());
			});
		}
	}

	@Override
	public void removeAllByCartId(Integer cartId) {
		// TODO Auto-generated method stub
		cartItemRepository.removeAllItems(cartId);
		cartItemRepository.flush();
	}

}
