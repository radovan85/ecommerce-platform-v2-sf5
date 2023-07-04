package com.radovan.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.radovan.spring.dto.CartDto;
import com.radovan.spring.dto.CartItemDto;
import com.radovan.spring.dto.CustomerDto;
import com.radovan.spring.dto.ProductDto;
import com.radovan.spring.service.CartItemService;
import com.radovan.spring.service.CartService;
import com.radovan.spring.service.CustomerService;
import com.radovan.spring.service.ProductService;

@Controller
@RequestMapping(value = "/cart")
public class CartController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CartService cartService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private ProductService productService;

	@GetMapping(value = "/viewCart")
	public String getCart(ModelMap map) {

		CustomerDto customer = customerService.getCurrentCustomer();
		CartDto cart = cartService.getCartByCartId(customer.getCartId());
		List<CartItemDto> allCartItems = cartItemService.listAllItems(cart.getCartId());
		List<ProductDto> allProducts = productService.listAll();
		Float fullPrice = cartService.calculateFullPrice(cart.getCartId());
		map.put("allCartItems", allCartItems);
		map.put("allProducts", allProducts);
		map.put("fullPrice", fullPrice);
		map.put("cart", cart);
		return "fragments/cart :: ajaxLoadedContent";
	}

	@GetMapping(value = "/add/{productId}")
	public String addCartItem(@PathVariable(value = "productId") Integer productId) {
		cartItemService.addCartItem(productId);
		return "fragments/homePage :: ajaxContentLoaded";
	}

	@GetMapping(value = "/removeCartItem/{cartId}/{itemId}")
	public String removeCartItem(@PathVariable(value = "cartId") Integer cartId,
			@PathVariable(value = "itemId") Integer itemId) {
		cartItemService.removeCartItem(itemId);
		return "fragments/homePage :: ajaxContentLoaded";
	}

	@GetMapping(value = "/removeAllItems/{cartId}")
	public String removeAllCartItems(@PathVariable(value = "cartId") Integer cartId) {
		cartItemService.removeAllCartItems();
		return "fragments/homePage :: ajaxContentLoaded";
	}
}
