package com.example.responsemodel;

import java.util.List;

public class Response {

	private String userId;
	private List<OrderResponse> orders = null;

	public Response(String userId, List<OrderResponse> orders) {
		super();
		this.userId = userId;
		this.orders = orders;
	}

	public Response() {
		super();
	}

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