package com.idega.block.trade.stockroom.data;

import com.idega.data.IDOEntity;

public interface DiscountCodeGroup extends IDOEntity {
	public float getDiscount();
	public void setDiscount(float discount);
	public void setSupplier(Supplier supplier);
	public void setSupplierId(Object supplierId);
	public boolean isValid();
	public void setValid(boolean deleted);
	public String getDiscountCodeGroupName();
	public void setDiscountCodeGroupName(String name);
}
