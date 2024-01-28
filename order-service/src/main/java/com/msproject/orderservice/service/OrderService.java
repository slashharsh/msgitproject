package com.msproject.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.msproject.orderservice.dto.InventoryResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msproject.orderservice.dto.OrderLineItemsDto;
import com.msproject.orderservice.dto.OrderRequest;
import com.msproject.orderservice.model.Order;
import com.msproject.orderservice.model.OrderLineItems;
import com.msproject.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
	
	private final OrderRepository orderRepository;
	private final WebClient.Builder webClientBuilder;

	public void placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		
		List<OrderLineItems> orderLineItems =  orderRequest.getOrderLineItemsDtoList()
		.stream()
		.map(this::mapToDto)
		.toList();
		
		order.setOrderLineItemsList(orderLineItems);
		List<String> skuCodes =  order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();
		// Call inventory service and checking if product is in stock
		InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
				.uri("http://inventory-service/api/inventory",
						uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
				.retrieve()
				.bodyToMono(InventoryResponse[].class)
				.block();
		Boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::getIsInStock);

		if(Boolean.TRUE.equals(allProductsInStock)) {
			orderRepository.save(order);

		} else {
			throw new IllegalArgumentException("Product is not in stock. Please try again later!");
		}
	}

	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
		orderLineItems.setPrice(orderLineItemsDto.getPrice());
		orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
		return orderLineItems;
	}
	
}
