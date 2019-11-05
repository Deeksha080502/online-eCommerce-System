package com.example.demo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.Cart;
import com.example.demo.model.Order;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.OrderRepository;
import com.example.responsemodel.Item;
import com.example.responsemodel.OrderResponse;
import com.example.responsemodel.Response;

@Component
public class Cartserviceimpl implements Myservice {

	@Autowired
	CartRepository cartRepository;

	@Autowired
	OrderRepository orderRepository;

	/**
	 * Method Use to retrieve cart items
	 * 
	 * @return Cart List
	 */
	@Override
	public List<Cart> retriveCart() {
		// TODO Auto-generated method stub
		return cartRepository.findAll();
	}

	/**
	 * Method Use to retrieve all orders
	 * 
	 * @return Order list
	 */
	@Override
	public List<Order> retriveOrder() {
		// TODO Auto-generated method stub
		return orderRepository.findAll();
	}

	/**
	 * Method Use to insert order into database
	 * 
	 * @param orderList
	 * @return all order list
	 */
	@Override
	public List<Order> createOrder(List<Order> orderList) {
		// TODO Auto-generated method stub
		return orderRepository.saveAll(orderList);
	}

	/**
	 * Method Use to delete data from cart
	 * 
	 * @return
	 */
	@Override
	public boolean deleteCart() {
		// TODO Auto-generated method stub
		cartRepository.deleteAll();
		return true;
	}

	/**
	 * Method Use Save cart into database
	 * 
	 * @param cart
	 * @return Return Cart Model class after insert intos database
	 */
	@Override
	public Cart saveCart(Cart cart) {
		if (cart == null)
			return null;
		return cartRepository.save(cart);
	}

	/**
	 * Method Use to oder list of user
	 * 
	 * @param user_id
	 * @return order list
	 */
	@Override
	public List<Order> getOrders(String user_id) {
		// TODO Auto-generated method stub
		return orderRepository.findByUserID(user_id);
	}

	/**
	 * Method Use to get orders using order_id
	 * 
	 * @param order_id
	 * @return Order list
	 */
	@Override
	public List<Order> getOrder(String order_id) {
		// TODO Auto-generated method stub
		return orderRepository.findByOrderID(order_id);
	}

	/**
	 * Method use to generate order list and save it into database
	 * 
	 * @return order list
	 */
	public List<Order> placeOrder() {
		// TODO Auto-generated method stub
		List<Cart> cart = retriveCart();
		List<Order> order = retriveOrder();

		List<Order> newOrder = new ArrayList<Order>();
		if (cart.size() > 0) {
			newOrder = insertDataInOrder(cart, order);
			newOrder = createOrder(newOrder);

			deleteCart();
		}
		return newOrder;
	}

	/**
	 * Method use to create Order pojo using cart pojo class
	 * 
	 * @param cart  list
	 * @param order list
	 * @return order list from database
	 */
	private List<Order> insertDataInOrder(List<Cart> cart, List<Order> order) {
		// TODO Auto-generated method stub
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
		}
		return newOrder;
	}

	/**
	 * Method use to generate response of order list of single user as required
	 * format
	 * 
	 * @param user_id
	 * @param order   list
	 * @return Response Model
	 */
	public Response getFormattedResponse(String user_id) {
		// TODO Auto-generated method stub
		List<Order> order = getOrders(user_id);

		Response response = new Response();
		response.setUserId(user_id);

		List<OrderResponse> orderResponse = getOrderResponse(order);

		response.setOrders(orderResponse);
		return response;
	}

	/**
	 * Method Use for insert data into order response pojo class using order list
	 * 
	 * @param order list
	 * @return orderResponse list
	 */
	private List<OrderResponse> getOrderResponse(List<Order> order) {
		List<OrderResponse> orderResponse = new ArrayList<OrderResponse>();
		// TODO Auto-generated method stub
		for (int i = 0; i < order.size(); i++) {
			if (i == 0) {
				OrderResponse orderResponseObj = new OrderResponse();
				orderResponseObj.setOrderId(Integer.valueOf(order.get(i).getOrder_id()));

				orderResponseObj.setPurchaseDate(order.get(i).getTimeStamp().toString());

				List<Item> items = new ArrayList<Item>();

				Item item = addItem(order.get((i)));

				items.add(item);

				orderResponseObj.setOrderTotal(item.getProductTotal());
				orderResponseObj.setItems(items);
				orderResponse.add(orderResponseObj);
				continue;
			}

			for (int j = 0; j < orderResponse.size(); j++) {
				if (order.get(i).getOrder_id().equalsIgnoreCase(String.valueOf(orderResponse.get(j).getOrderId()))) {
					Item item = addItem(order.get((i)));
					orderResponse.get(j).setOrderTotal(orderResponse.get(j).getOrderTotal() + item.getProductTotal());
					orderResponse.get(j).getItems().add(item);

				} else {

					OrderResponse orderResponseObj = new OrderResponse();
					orderResponseObj.setOrderId(Integer.valueOf(order.get(i).getOrder_id()));
					orderResponseObj.setPurchaseDate(order.get(i).getTimeStamp().toString());
					List<Item> items = new ArrayList<Item>();
					Item item = addItem(order.get((i)));
					items.add(item);
					orderResponseObj.setOrderTotal(item.getProductTotal());
					orderResponseObj.setItems(items);
					orderResponse.add(orderResponseObj);
				}

			}
		}
		return orderResponse;
	}

	/**
	 * Method use to add data in item model class
	 * 
	 * @param order
	 * @return item model class
	 */
	private Item addItem(Order order) {
		// TODO Auto-generated method stub
		Item item = new Item();
		item.setProductId(order.getProduct_id());
		item.setProductName(order.getProduct_name());
		item.setQuantity(Integer.parseInt(order.getQuantity()));
		item.setProductPrice(Double.parseDouble((order.getPrice())));
		item.setProductTotal((Double.parseDouble((order.getPrice())) * Integer.parseInt(order.getQuantity())));
		return item;
	}

	/**
	 * Method use to generate response of single order detail as required format
	 * 
	 * @param order_id
	 * @param order
	 * @return
	 */
	public OrderResponse getResponseOfSingalOrderDetail(String order_id) {
		// TODO Auto-generated method stub
		List<Order> order = getOrder(order_id);

		Double OrderTotal = 0.0;

		OrderResponse orderResponse = new OrderResponse();
		orderResponse.setOrderId(Integer.valueOf(order_id));

		List<Item> Items = new ArrayList<Item>();

		for (int i = 0; i < order.size(); i++) {

			Item item = new Item();
			System.out.println("Product_id if" + order.get(i).getProduct_id());
			item.setProductId(order.get(i).getProduct_id());
			item.setProductName(order.get(i).getProduct_name());

			item.setQuantity(Integer.parseInt(order.get(i).getQuantity()));
			item.setProductPrice(Double.parseDouble((order.get(i).getPrice())));

			item.setProductTotal(
					(Double.parseDouble((order.get(i).getPrice())) * Integer.parseInt(order.get(i).getQuantity())));
			OrderTotal = OrderTotal + item.getProductTotal();
			Items.add(item);

			if (i == 0) {
				orderResponse.setPurchaseDate(order.get(i).getTimeStamp().toString());

			}

		}

		orderResponse.setOrderTotal(OrderTotal);

		orderResponse.setItems(Items);
		return orderResponse;
	}

}
