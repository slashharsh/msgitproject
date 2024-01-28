package com.msproject.inventoryservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.msproject.inventoryservice.model.Inventory;
import com.msproject.inventoryservice.repository.InventoryRepository;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
		@Bean
		public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
			return args -> {
				Inventory inventory = new Inventory();
				inventory.setSkuCode("iphone13");
				inventory.setQuantity(1000);
				
				Inventory inventory1 = new Inventory();
				inventory1.setSkuCode("iphone13_red");
				inventory1.setQuantity(0);
				
				inventoryRepository.save(inventory);
				inventoryRepository.save(inventory1);
				
			};
		}

}
