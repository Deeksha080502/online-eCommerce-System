package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	private Integer cart_id;
    private String user_id;
    private Integer product_id;
	private String product_name;
    private String product_description;
	private String price;
	private String quantity;
	
	
	 
	public Integer getCart_id() {
		return cart_id;
	}



	public void setCart_id(Integer cart_id) {
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



	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}

	


	}
