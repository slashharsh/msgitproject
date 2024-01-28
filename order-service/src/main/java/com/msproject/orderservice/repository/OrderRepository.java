package com.msproject.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msproject.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
