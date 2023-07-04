package com.radovan.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.radovan.spring.dto.ProductCategoryDto;
import com.radovan.spring.dto.ProductDto;
import com.radovan.spring.service.ProductCategoryService;
import com.radovan.spring.service.ProductService;

@Controller
@RequestMapping(value = "/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductCategoryService categoryService;

	@GetMapping(value = "/allProducts")
	public String getAllProducts(ModelMap map) {
		List<ProductDto> allProducts = productService.listAll();
		List<ProductCategoryDto> allCategories = categoryService.listAll();
		map.put("allProducts", allProducts);
		map.put("allCategories", allCategories);
		map.put("recordsPerPage", 5);
		return "fragments/productList :: ajaxLoadedContent";
	}

	@GetMapping(value = "/getProduct/{productId}")
	public String getProductDetails(@PathVariable("productId") Integer productId, ModelMap map) {

		ProductDto currentProduct = productService.getProductById(productId);
		ProductCategoryDto category = categoryService.getCategoryById(currentProduct.getProductCategoryId());
		map.put("currentProduct", currentProduct);
		map.put("category", category);
		return "fragments/productDetails :: ajaxLoadedContent";
	}

	@GetMapping(value = "/searchProducts")
	public String searchProducts(@RequestParam("keyword") String keyword, ModelMap map) {

		List<ProductDto> searchResult = productService.listAllByKeyword(keyword);
		List<ProductCategoryDto> allCategories = categoryService.listAll();
		map.put("searchResult", searchResult);
		map.put("allCategories", allCategories);
		map.put("recordsPerPage", 5);
		return "fragments/searchList :: ajaxLoadedContent";
	}
}
