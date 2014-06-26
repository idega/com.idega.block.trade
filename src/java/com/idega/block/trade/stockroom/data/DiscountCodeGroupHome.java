package com.idega.block.trade.stockroom.data;

import java.util.Collection;

import com.idega.data.IDOHome;

public interface DiscountCodeGroupHome  extends IDOHome{
	public Collection getByProductId(Object productId,int start, int max);
	public Collection getBySupplierId(Object supplierPK,int start, int max);
}