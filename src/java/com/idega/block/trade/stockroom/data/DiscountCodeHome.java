package com.idega.block.trade.stockroom.data;

import java.util.Collection;

import com.idega.data.IDOHome;

public interface DiscountCodeHome  extends IDOHome{
	public DiscountCode getByCode(String code);
	public DiscountCode getByCodeAndProduct(String code,Object productId);
	public Collection getPKsByProductId(Object productId,int start, int max);
	public int countByProduct(Object productId);
	public Collection getBySupplier(Object supplierPK,int start, int max);
	public Collection getBySupplierNotUsed(Object supplierPK,int start, int max);
}
