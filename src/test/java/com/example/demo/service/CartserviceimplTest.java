/**
 * 
 */
package com.example.demo.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.model.Cart;
import com.example.demo.model.Order;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.OrderRepository;
import com.example.responsemodel.OrderResponse;
import com.example.responsemodel.Response;

/**
 * @author DeekshaSh
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CartserviceimplTest {

	@InjectMocks
	Cartserviceimpl service;

	@Mock
	OrderRepository orderRepository;
	@Mock
	CartRepository cartRepository;

	@Before(value = "")
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Test case to test Business Logic of insert cart data into table 
	 */
	@Test
	public void saveCart() {
		Cart mockCart = new Cart(101, "abc", 114, "car5", "Teatdatadf", "1001", "2");
		
		when(cartRepository.save(Mockito.any())).thenReturn(mockCart);
		Cart cart  = service.saveCart(mockCart);
		
		assertEquals(mockCart.getCart_id(), cart.getCart_id());
	}
	

	/**
	 * Test case to test Business Logic of place order
	 */
	@Test
	public void placeOrder() {
		Cart mockCart = new Cart(101, "abc", 114, "car5", "Teatdatadf", "1001", "2");
		
		List<Cart>cartList = new ArrayList<Cart>();
		cartList.add(mockCart);
		List<Order>orderList = new ArrayList<Order>();

		when(cartRepository.findAll()).thenReturn(cartList);
		when(orderRepository.findAll()).thenReturn(orderList);
		doAnswer((i) -> {
			return null;
		}).when(cartRepository).deleteAll();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime timeStamp = LocalDateTime.parse("2014-04-01 00:00:00", formatter);

		Order orderObj = new Order("2", "101", "abc", 112, "car5", "Teatdatadf", "1001", "2", timeStamp);

		orderList.add(orderObj);
		
		when(orderRepository.saveAll(Mockito.any())).thenReturn(orderList);

		List<Order>newOrderList = service.placeOrder();
		
		assertEquals(orderObj,newOrderList.get(0));
	}
	
	/**
	 * test case of Business logic to get orders in requires format
	 */
	@Test
	public void getFormattedResponse() {
		
		List<Order>orderList = new ArrayList<Order>();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime timeStamp = LocalDateTime.parse("2014-04-01 00:00:00", formatter);

		Order orderObj = new Order("2", "101", "abc", 112, "car5", "Teatdatadf", "1001", "2", timeStamp);
		Order orderObj1 = new Order("2", "101", "abc", 112, "car6", "Teatdatadf", "2001", "1", timeStamp);
		
		orderList.add(orderObj);
		orderList.add(orderObj1);
		
		when(orderRepository.findByUserID(Mockito.anyString())).thenReturn(orderList);
		
//		service.getOrderResponse(orderList);
		Response response = service.getFormattedResponse("abc");
		
		assertEquals("abc",response.getUserId());
	}
	
	/**
	 * test case of Business logic to get single orderDetail in requires format
	 */
	@Test
	public void getResponseOfSingalOrderDetail() {
		
		List<Order>orderList = new ArrayList<Order>();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime timeStamp = LocalDateTime.parse("2014-04-01 00:00:00", formatter);

		Order orderObj = new Order("2", "101", "abc", 112, "car5", "Teatdatadf", "1001", "2", timeStamp);

		orderList.add(orderObj);

		when(orderRepository.findByOrderID(Mockito.anyString())).thenReturn(orderList);
		
		OrderResponse orderResponse = service.getResponseOfSingalOrderDetail("1");
		System.out.println("getOrderId"+orderResponse.getOrderId());
		
		assertEquals(String.valueOf(1),orderResponse.getOrderId().toString());
	}

}
