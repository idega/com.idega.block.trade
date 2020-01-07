package com.idega.block.trade.stockroom.data;

public interface ConnectedProduct extends SideAd {
	Product getSideProduct();
	Integer getSideProductId();
	void setSideProduct(Integer id);
	void setSideProduct(Product id);
}