package com.example.demo.controller;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.model.Cart;
import com.example.demo.model.Order;
import com.example.demo.service.Cartserviceimpl;
import com.example.responsemodel.Item;
import com.example.responsemodel.OrderResponse;
import com.example.responsemodel.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CartController.class, secure = false)
public class CartControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean // This  annotation is useful in integration test where a particular bean-an external service-needs to be mock
	private Cartserviceimpl cartService;

	String exampleString = "{\"cart_id\":\"101\",\"user_id\":\"abc\",\"product_id\":114,\"product_name\":\"car5\",\"product_description\":\"Teatdatadf\",\"price\":\"1001\",\"quantity\":\"2\"}";

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	LocalDateTime timeStamp = LocalDateTime.parse("2014-04-01 00:00:00", formatter);

	Order orderObj = new Order("2", "101", "abc", 112, "car5", "Teatdatadf", "1001", "2", timeStamp);

	Cart mockCart = new Cart(101, "abc", 114, "car5", "Teatdatadf", "1001", "2");

	/**
	 * Test case for save cart Api
	 * @throws Exception
	 */
	@Test
	public void createCart() throws Exception {

		// studentService.addCourse to respond back with mockCourse
		Mockito.when(cartService.saveCart(Mockito.any(Cart.class))).thenReturn(mockCart);

		// Send course as body to /students/Student1/courses
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/saveCart")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(exampleString);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		System.out.println("responsestatus" + response.getStatus());

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	/**
	 * Test case for place order
	 * @throws Exception
	 */
	@Test
	public void placeOrder() throws Exception {

		orderObj.setId(8L);
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(orderObj);

		Mockito.when(cartService.placeOrder()).thenReturn(orderList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/placeOrder").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println("responsePlaceOrder" + result.getResponse().getContentAsString());

		
		String json = asJsonString(orderList);

		System.out.println("json *** " + json);
		
		JSONArray arr = new JSONArray(result.getResponse()
				.getContentAsString());
		

		JSONAssert.assertEquals(orderList.get(0).getCart_id(), arr.getJSONObject(0).getString("cart_id"), true);

//		[{"id":8,"order_id":"2","cart_id":"101","user_id":"abc","product_id":112,"product_name":"car5","product_description":"Teatdatadf","price":"1001","quantity":"2","timeStamp":"2014-04-01T00:00:00"}]
//		[{"id":8,"order_id":"2","cart_id":"101","user_id":"abc","product_id":112,"product_name":"car5","product_description":"Teatdatadf","price":"1001","quantity":"2","time_stamp":{"date":{"year":2014,"month":4,"day":1},"time":{"hour":0,"minute":0,"second":0,"nano":0}}}]

	}

	/**
	 * Test case for getAllOrder of User Api  
	 * @throws Exception
	 */
	@Test
	public void getAllOrder() throws Exception {

		List<OrderResponse> orderResponseList = new ArrayList<OrderResponse>();

		Item itemObj = new Item(114, "car6", 2, 1001.0, 2002.0);

		List<Item> item = new ArrayList<Item>();
		item.add(itemObj);
		OrderResponse orderResponse = new OrderResponse(1, "2014-04-01T00:00", 2002.0, item);

		orderResponseList.add(orderResponse);

		Response response = new Response("abc", orderResponseList);

		Mockito.when(cartService.getFormattedResponse(Mockito.anyString()
				)).thenReturn(response);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getAllOrder/abc")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//		System.out.println("responsegetAllOrder" + result.getResponse().getContentAsString());
//		System.out.println("responseResponse"+asJsonString(response));
		
//		{"userId":"abc","orders":[{"orderId":1,"purchaseDate":"2014-04-01T00:00","orderTotal":2002.0,"items":[{"productId":114,"productName":"car6","quantity":2,"productPrice":1001.0,"productTotal":2002.0}]}]}
		
		JSONAssert.assertEquals(asJsonString(response),result.getResponse().getContentAsString(), true);
	}
	
	
	/**
	 * Test case for getSingalOrderDetail of order id Api
	 * @throws Exception
	 */
	@Test
	public void getSingalOrderDetail() throws Exception {

		List<OrderResponse> orderResponseList = new ArrayList<OrderResponse>();

		Item itemObj = new Item(114, "car6", 2, 1001.0, 2002.0);

		List<Item> item = new ArrayList<Item>();
		item.add(itemObj);
		OrderResponse orderResponse = new OrderResponse(1, "2014-04-01T00:00", 2002.0, item);

		orderResponseList.add(orderResponse);

	
		Mockito.when(cartService.getResponseOfSingalOrderDetail(Mockito.anyString())).thenReturn(orderResponse);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getSingalOrderDetail/1")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//		System.out.println("responsegetSingalOrderDetail" + result.getResponse().getContentAsString());
//		System.out.println("responseOrderResponse"+asJsonString(orderResponse));
		
//		{"orderId":1,"purchaseDate":"2014-04-01T00:00","orderTotal":2002.0,"items":[{"productId":114,"productName":"car6","quantity":2,"productPrice":1001.0,"productTotal":2002.0}]}		
	
		JSONAssert.assertEquals(asJsonString(orderResponse),result.getResponse().getContentAsString(), true);
	}
	
	
	/**
	 * Method Use get data in String form of Model class
	 * @param obj Model class
	 * @return data in string
	 */
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
