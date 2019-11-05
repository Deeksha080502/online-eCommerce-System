package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Cart;
import com.example.demo.model.Order;
import com.example.demo.service.Cartserviceimpl;
import com.example.responsemodel.OrderResponse;
import com.example.responsemodel.Response;

@RestController
public class CartController {

	@Autowired
	Cartserviceimpl Cartservice;

	/**
	 * Method use create cart table
	 * 
	 * @param cart
	 * @return
	 */
	@PostMapping("/saveCart")
	@ResponseBody
	public Cart insertCart(@RequestBody Cart cart) {
		Cart cartObj = new Cart();
		if (Cartservice.saveCart(cart) != null)
			cartObj = Cartservice.saveCart(cart);

		return cartObj;
	}

	/**
	 * Method use for place order of cart item
	 * 
	 * @return List of Order
	 */
	@GetMapping("/placeOrder")
	@ResponseBody
	public List<Order> placeOrder() {
		List<Order> newOrder = Cartservice.placeOrder();
		if (newOrder.size() == 0) {
			Order order = new Order();
			newOrder.add(order);
		}
		return newOrder;
	}

	/**
	 * Method use for get all carts
	 * 
	 * @return list of cart
	 */
	@GetMapping("/cart")
	public List<Cart> retriveCart() {
		return Cartservice.retriveCart();
	}

	/**
	 * method use for get all orders
	 * 
	 * @return list of order
	 */
	@GetMapping("/order")
	public List<Order> retriveOrder() {
		return Cartservice.retriveOrder();
	}

	/**
	 * Method use to get all orders of a user
	 * 
	 * @param user_id
	 * @return Response model class
	 */
	@GetMapping("/getAllOrder/{user_id}")
	public Response getAllOrder(@PathVariable String user_id) {

		return Cartservice.getFormattedResponse(user_id);
	}

	/**
	 * Method use to get single order detail of order id
	 * 
	 * @param order_id
	 * @return Order Response model class
	 */
	@GetMapping("/getSingalOrderDetail/{order_id}")
	public OrderResponse getSingalOrderDetail(@PathVariable String order_id) {

		return Cartservice.getResponseOfSingalOrderDetail(order_id);
	}

}
