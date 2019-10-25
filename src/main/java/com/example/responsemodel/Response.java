package com.example.responsemodel;

import java.util.List;

public class Response {

	private String userId;
	private List<OrderResponse> orders = null;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<OrderResponse> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderResponse> orders) {
		this.orders = orders;
	}

	
}