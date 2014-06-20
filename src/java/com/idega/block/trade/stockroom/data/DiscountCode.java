package com.idega.block.trade.stockroom.data;

import java.util.Collection;

import com.idega.data.IDOEntity;

public interface DiscountCode extends IDOEntity {
	public String getCode();
	public void setCode(String code);
	
	public boolean isUsed();
	public void setUsed(boolean used);
	public int getTimesUsed();
	public void setTimesUsed(int times);
	public float getDiscount();
	public void setDiscount(float discount);
	public void addProduct(Product product);
	public void removeProduct(Product product);
	public Collection getProducts();
	public void setSupplier(Supplier supplier);
	public void setSupplierId(Object supplierId);
	public boolean getDeleted();
	public void setDeleted(boolean deleted);

}
