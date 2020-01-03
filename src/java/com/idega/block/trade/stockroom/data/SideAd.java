package com.idega.block.trade.stockroom.data;


import com.idega.data.IDOLegacyEntity;

public interface SideAd extends IDOLegacyEntity {
	
	public static final Integer TYPE_FILE_LINK = new Integer(0);
	public static final Integer TYPE_SIDE_PRODUCT = new Integer(1);
	
	public Product getProduct();
	public void setProduct(Integer id);
	public void setProduct(Product id);
	
	public Integer getOrder();
	public void setOrder(Integer order);
	
	public Integer getType();
	public void setType(Integer type);
}