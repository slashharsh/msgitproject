package com.msproject.productservice;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msproject.productservice.dto.ProductRequest;
import com.msproject.productservice.repository.ProductRepository;

@SpringBootTest
@AutoConfigureMockMvc
class ProductServiceApplicationTests {
	
	@Container
	static MongoDBContainer mongoDbContainer = new MongoDBContainer("mongo:4.4.2");

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ProductRepository productRepository;
	
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDbContainer::getReplicaSetUrl);
	}
	
	static {
		mongoDbContainer.start();
	}
	
	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String reqString = objectMapper.writeValueAsString(productRequest);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(reqString))
				.andExpect(status().isCreated());
		
		Assertions.assertEquals(1, productRepository.findAll().size());
	}

	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("Pencil")
				.description("Black Pencil")
				.price(BigDecimal.valueOf(10))
				.build();
	}

}
