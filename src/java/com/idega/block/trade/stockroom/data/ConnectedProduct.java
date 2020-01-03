package com.idega.block.trade.stockroom.data;

public interface ConnectedProduct extends SideAd {
	public Product getSideProduct();
	public void setSideProduct(Integer id);
	public void setSideProduct(Product id);
}