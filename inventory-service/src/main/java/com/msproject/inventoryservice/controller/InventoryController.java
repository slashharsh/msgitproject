package com.msproject.inventoryservice.controller;

import com.msproject.inventoryservice.dto.InventoryResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.msproject.inventoryservice.service.InventoryService;

import lombok.RequiredArgsConstructor;

import java.util.List;


@RestController
@RequestMapping(value = "/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
	
	private final InventoryService inventoryService;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
		return inventoryService.isInStock(skuCode);
	}

}
