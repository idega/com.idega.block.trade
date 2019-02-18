package com.idega.block.trade.stockroom.bean;

import java.io.Serializable;

public class SideProductSearchItem implements Serializable{
	private static final long serialVersionUID = 7026320258474599968L;
	
	private String id;
	private String name;
	private String imageUrl;
	private boolean inSideProducts;
	private Integer order;
	
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public boolean isInSideProducts() {
		return inSideProducts;
	}
	public void setInSideProducts(boolean inSideProducts) {
		this.inSideProducts = inSideProducts;
	}
}
