package com.example.responsemodel;


public class Item {

public Item(Integer productId, String productName, Integer quantity, Double productPrice, Double productTotal) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.productPrice = productPrice;
		this.productTotal = productTotal;
	}

public Item() {
	super();
}

private Integer productId;
private String productName;
private Integer quantity;
private Double productPrice;
private Double productTotal;

public Integer getProductId() {
return productId;
}

public void setProductId(Integer productId) {
this.productId = productId;
}

public String getProductName() {
return productName;
}

public void setProductName(String productName) {
this.productName = productName;
}

public Integer getQuantity() {
return quantity;
}

public void setQuantity(Integer quantity) {
this.quantity = quantity;
}

public Double getProductPrice() {
return productPrice;
}

public void setProductPrice(Double productPrice) {
this.productPrice = productPrice;
}

public Double getProductTotal() {
return productTotal;
}

public void setProductTotal(Double productTotal) {
this.productTotal = productTotal;
}



}
