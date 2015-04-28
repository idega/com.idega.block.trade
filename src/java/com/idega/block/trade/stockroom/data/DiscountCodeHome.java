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
	public int countBySupplierAndCode(Object supplierPk, String code);
	public int countByCodeGroupAndCode(Object codeGroupPk, String code);
	public DiscountCode getByCodeGroupAndCode(Object codeGroupPk, String code);
	public int countByProductDepartures(Object productId);
	public boolean isCodesForProduct(Object productId,Object departureId);
	public DiscountCode getByCodeAndProductAndDeparture(String code, Object productId, Object departureId);
	public DiscountCode getByCodeGroupAndCodeNotUsed(Object codeGroupPk, String code);
}
