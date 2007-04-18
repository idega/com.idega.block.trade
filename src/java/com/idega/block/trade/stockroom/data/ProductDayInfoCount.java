package com.idega.block.trade.stockroom.data;


import java.sql.Date;
import com.idega.data.IDOEntity;

public interface ProductDayInfoCount extends IDOEntity {
	/**
	 * @see com.idega.block.trade.stockroom.data.ProductDayInfoCountBMPBean#getProductId
	 */
	public int getProductId();

	/**
	 * @see com.idega.block.trade.stockroom.data.ProductDayInfoCountBMPBean#getDate
	 */
	public Date getDate();

	/**
	 * @see com.idega.block.trade.stockroom.data.ProductDayInfoCountBMPBean#getCount
	 */
	public int getCount();

	/**
	 * @see com.idega.block.trade.stockroom.data.ProductDayInfoCountBMPBean#setProductId
	 */
	public void setProductId(int productId);

	/**
	 * @see com.idega.block.trade.stockroom.data.ProductDayInfoCountBMPBean#setDate
	 */
	public void setDate(Date date);

	/**
	 * @see com.idega.block.trade.stockroom.data.ProductDayInfoCountBMPBean#setCount
	 */
	public void setCount(int count);

	/**
	 * @see com.idega.block.trade.stockroom.data.ProductDayInfoCountBMPBean#setTimeframeId
	 */
	public void setTimeframeId(int timeframeId);

	/**
	 * @see com.idega.block.trade.stockroom.data.ProductDayInfoCountBMPBean#getTimeframeId
	 */
	public int getTimeframeId();

	/**
	 * @see com.idega.block.trade.stockroom.data.ProductDayInfoCountBMPBean#setAddressId
	 */
	public void setAddressId(int addressId);

	/**
	 * @see com.idega.block.trade.stockroom.data.ProductDayInfoCountBMPBean#getAddressId
	 */
	public int getAddressId();
}