package com.example.responsemodel;

import java.util.List;

public class OrderResponse {
	private Integer orderId;
	private String purchaseDate;
	private Double orderTotal;
	private List<Item> items = null;

	public Integer getOrderId() {
	return orderId;
	}

	public OrderResponse(Integer orderId, String purchaseDate, Double orderTotal, List<Item> items) {
		super();
		this.orderId = orderId;
		this.purchaseDate = purchaseDate;
		this.orderTotal = orderTotal;
		this.items = items;
	}

	public OrderResponse() {
		super();
	}

	public void setOrderId(Integer orderId) {
	this.orderId = orderId;
	}

	public String getPurchaseDate() {
	return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
	this.purchaseDate = purchaseDate;
	}

	public Double getOrderTotal() {
	return orderTotal;
	}

	public void setOrderTotal(Double orderTotal) {
	this.orderTotal = orderTotal;
	}

	public List<Item> getItems() {
	return items;
	}

	public void setItems(List<Item> items) {
	this.items = items;
	}

	}