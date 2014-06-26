package com.idega.block.trade.stockroom.data;

import com.idega.data.IDOEntity;

public interface DiscountCode  extends IDOEntity{
	public String getCode();
	public void setCode(String code);
	public boolean isUsed();
	public void setUsed(boolean used);
	public int getTimesUsed();
	public void setTimesUsed(int times);
	public DiscountCodeGroup getDiscountCodeGroup();
	public void setDiscountCodeGroup(DiscountCodeGroup discountCodeGroup);
	public boolean isValid();
	public void setValid(boolean valid);
	public void setDiscountCodeGroupPk(Object discountCodeGroupPk);
}
