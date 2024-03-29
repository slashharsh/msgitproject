package com.msproject.productservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.msproject.productservice.dto.ProductRequest;
import com.msproject.productservice.dto.ProductResponse;
import com.msproject.productservice.service.ProductService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/product")
public class ProductController {
	
	private final ProductService productService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createProduct(@RequestBody ProductRequest productRequest) {
		productService.createProduct(productRequest);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<ProductResponse> getAllProducts() {
		return productService.getAllProducts();
	}

}
