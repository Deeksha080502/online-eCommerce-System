package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Order;

public interface OrderRepository extends JpaRepository<Order,Integer>  {
	
	@Query(value = "SELECT * FROM ORDER_TBL WHERE USER_ID = ?1", nativeQuery = true)
	  List<Order> findByUserID(String user_id);
	
	@Query(value = "SELECT * FROM ORDER_TBL WHERE ORDER_ID = ?1", nativeQuery = true)
	  List<Order> findByOrderID(String order_id);

}
