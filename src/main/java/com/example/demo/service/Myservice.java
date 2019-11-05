package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Cart;
import com.example.demo.model.Order;

/**
 * @author DeekshaSh
 * 
 */

public interface Myservice {

	/**
	 * Method Use Save cart into database
	 * @param cart
	 * @return Return Cart Model class after insert intos database
	 */
	public Cart saveCart(Cart cart);

	/**
	 * Method Use to retrieve cart items
	 * 
	 * @return Cart List
	 */
	public List<Cart> retriveCart();

	/**
	 * Method Use to retrieve all orders
	 * 
	 * @return Order list
	 */
	public List<Order> retriveOrder();

	/**
	 * Method Use to insert order into database
	 * 
	 * @param orderList
	 * @return all order list
	 */
	public List<Order> createOrder(List<Order> orderList);

	/**
	 * Method Use to delete data from cart
	 * 
	 * @return
	 */
	public boolean deleteCart();

	/**
	 * Method Use to oder list of user
	 * 
	 * @param user_id
	 * @return order list
	 */
	public List<Order> getOrders(String user_id);

	/**
	 * Method Use to get orders using order_id
	 * 
	 * @param order_id
	 * @return Order list
	 */
	public List<Order> getOrder(String order_id);

}
