package com.example.demo.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Cart;
import com.example.demo.model.Order;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.OrderRepository;
import com.example.responsemodel.Item;
import com.example.responsemodel.OrderResponse;
import com.example.responsemodel.Response;

import org.springframework.data.domain.Sort;

@RestController
public class CartController {

	@Autowired
	CartRepository cartRepository;

	@Autowired
	OrderRepository orderRepository;


	@PostMapping("/saveCart")
	public void insertCart(@RequestBody Cart cart) {
		cartRepository.save(cart);
		// System.out.println("Successful"+cart.getProduct_Name());

	}

	@GetMapping("/placeOrder")
	public void getOrder() {

		List<Cart> cart = cartRepository.findAll();

		List<Order> order = orderRepository.findAll();

		String order_id = "1";

		if (order != null && order.size() > 0) {

			order_id = order.get(order.size() - 1).getOrder_id();
			order_id = String.valueOf(Integer.parseInt(order_id) + 1);

		}

		List<Order> newOrder = new ArrayList<Order>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		for (int i = 0; i < cart.size(); i++) {

			LocalDateTime timeStamp = LocalDateTime.parse("2014-04-01 00:00:00", formatter);

			Order orderObj = new Order(order_id, String.valueOf(cart.get(i).getCart_id()), cart.get(i).getUser_id(),
					cart.get(i).getProduct_id(), cart.get(i).getProduct_name(), cart.get(i).getProduct_description(),
					cart.get(i).getPrice(), cart.get(i).getQuantity(), timeStamp);
			newOrder.add(orderObj);
//		  order.set(index, element) ;

		}
		orderRepository.saveAll(newOrder);

		cartRepository.deleteAll();

	}

	@GetMapping("/cart")
	public List<Cart> retriveAllStudent() {
		return cartRepository.findAll();
	}

	
	@GetMapping("/getAllOrder/{user_id}")
	public Response  getAllOrder(@PathVariable String user_id) {
		List<Order> order = orderRepository.findByUserID(user_id);
		Response response = new Response();
		response.setUserId(user_id);
		
		
		List<OrderResponse> orderResponse =new ArrayList<OrderResponse>();
//		OrderResponse orderResponseObj1 = new OrderResponse();
//		orderResponse.add(orderResponseObj1);
		
		for(int i =0;i<order.size();i++) {
			if(i==0) {
				OrderResponse orderResponseObj = new OrderResponse();
				orderResponseObj.setOrderId(Integer.valueOf(order.get(i).getOrder_id()));
			
				orderResponseObj.setPurchaseDate(order.get(i).getTimeStamp().toString());
				
				List<Item> items = new ArrayList<Item>();
				
				Item item = new Item();
				item.setProductId(order.get(i).getProduct_id());
				item.setProductName(order.get(i).getProduct_name());
				item.setQuantity(Integer.parseInt(order.get(i).getQuantity()));
				item.setProductPrice(Double.parseDouble((order.get(i).getPrice())));
				item.setProductTotal((Double.parseDouble((order.get(i).getPrice()))*Integer.parseInt(order.get(i).getQuantity())));
				
				items.add(item);
				
				orderResponseObj.setOrderTotal(item.getProductTotal());
				orderResponseObj.setItems(items);
				orderResponse.add(orderResponseObj);
				continue;
			}
			
			for(int j=0;j<orderResponse.size();j++) {
					if(order.get(i).getOrder_id().equalsIgnoreCase(String.valueOf(orderResponse.get(j).getOrderId()))) {
						Item item = new Item();
						item.setProductId(order.get(i).getProduct_id());
						item.setProductName(order.get(i).getProduct_name());

						item.setQuantity(Integer.parseInt(order.get(i).getQuantity()));
						item.setProductPrice(Double.parseDouble((order.get(i).getPrice())));

						item.setProductTotal((Double.parseDouble((order.get(i).getPrice()))*Integer.parseInt(order.get(i).getQuantity())));
						
						orderResponse.get(j).setOrderTotal(orderResponse.get(j).getOrderTotal()+item.getProductTotal());
						orderResponse.get(j).getItems().add(item);

					}else {
						
						OrderResponse orderResponseObj = new OrderResponse();
						orderResponseObj.setOrderId(Integer.valueOf(order.get(i).getOrder_id()));
					
						orderResponseObj.setPurchaseDate(order.get(i).getTimeStamp().toString());
						
						List<Item> items = new ArrayList<Item>();
						
						Item item = new Item();
						item.setProductId(order.get(i).getProduct_id());
						item.setProductName(order.get(i).getProduct_name());
						item.setQuantity(Integer.parseInt(order.get(i).getQuantity()));
						item.setProductPrice(Double.parseDouble((order.get(i).getPrice())));
						item.setProductTotal((Double.parseDouble((order.get(i).getPrice()))*Integer.parseInt(order.get(i).getQuantity())));
						
						items.add(item);
						
						orderResponseObj.setOrderTotal(item.getProductTotal());
						orderResponseObj.setItems(items);
						orderResponse.add(orderResponseObj);
					}
					
				}
			
		}
	
		response.setOrders(orderResponse);
		return response ;
	}
	
	
	@GetMapping("/getSingalOrderDetail/{order_id}")
	public OrderResponse  getSingalOrderDetail(@PathVariable String order_id) {
		List<Order> order = orderRepository.findByOrderID(order_id);
		System.out.println("order.size()"+order.size());
		
		boolean isFirstTime = false;
		Double OrderTotal =0.0;
		
		OrderResponse orderResponse = new OrderResponse();
		orderResponse.setOrderId(Integer.valueOf(order_id));
		
		List<Item> Items = new ArrayList<Item>();
//		Item itemObj = new Item();
//		Items.add(itemObj);
		
		for(int i =0;i<order.size();i++) {
			
		
						Item item = new Item();
						System.out.println("Product_id if"+order.get(i).getProduct_id());
						item.setProductId(order.get(i).getProduct_id());
						item.setProductName(order.get(i).getProduct_name());

						item.setQuantity(Integer.parseInt(order.get(i).getQuantity()));
						item.setProductPrice(Double.parseDouble((order.get(i).getPrice())));

						item.setProductTotal((Double.parseDouble((order.get(i).getPrice()))*Integer.parseInt(order.get(i).getQuantity())));
						OrderTotal = OrderTotal +item.getProductTotal();
						Items.add(item);
						
						if(!isFirstTime) {
						orderResponse.setPurchaseDate(order.get(i).getTimeStamp().toString());
						
						isFirstTime = true;
						}

//					
//					
//				}
			
		}
		
		orderResponse.setOrderTotal(OrderTotal);
	
		orderResponse.setItems(Items);
		return orderResponse ;
	}


}
