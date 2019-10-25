package com.example.demo.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="order_tbl")
public class Order  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String order_id;
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	private String cart_id;
    private String user_id;
    private Integer product_id;
	private String product_name;
    private String product_description;
	private String price;
	private String quantity;
	public LocalDateTime getTimeStamp() {
		return time_stamp;
	}
	public void setTimeStamp(LocalDateTime timeStamp) {
		this.time_stamp = timeStamp;
	}
	private LocalDateTime time_stamp;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCart_id() {
		return cart_id;
	}
	public void setCart_id(String cart_id) {
		this.cart_id = cart_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_description() {
		return product_description;
	}
	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Order( String order_id, String cart_id, String user_id, Integer product_id, String product_name,
			String product_description, String price, String quantity,LocalDateTime timeStamp) {
		super();
		
		this.order_id = order_id;
		this.cart_id = cart_id;
		this.user_id = user_id;
		this.product_id = product_id;
		this.product_name = product_name;
		this.product_description = product_description;
		this.price = price;
		this.quantity = quantity;
		this.time_stamp = timeStamp;
	}
	
	
	
	
}
