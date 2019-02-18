package com.idega.block.trade.stockroom.data;


import com.idega.data.IDOLegacyEntity;

public interface SideProduct extends IDOLegacyEntity {
	
	public Product getProduct();
	public Product getSideProduct();
	public void setProduct(Integer id);
	public void setSideProduct(Integer id);
	public void setProduct(Product id);
	public void setSideProduct(Product id);
}