package com.idega.block.trade.stockroom.data;


import java.sql.Timestamp;
import java.util.Collection;

import com.idega.core.location.data.Address;
import com.idega.data.IDOLegacyEntity;
import com.idega.util.IWTimestamp;

public interface TravelAddress extends IDOLegacyEntity {
	/**
	 * @see com.idega.block.trade.stockroom.data.TravelAddressBMPBean#getAddressId
	 */
	public int getAddressId();

	/**
	 * @see com.idega.block.trade.stockroom.data.TravelAddressBMPBean#getAddress
	 */
	public Address getAddress();

	/**
	 * @see com.idega.block.trade.stockroom.data.TravelAddressBMPBean#getTimeString
	 */
	public String getTimeString();

	/**
	 * @see com.idega.block.trade.stockroom.data.TravelAddressBMPBean#getName
	 */
	public String getName();

	/**
	 * @see com.idega.block.trade.stockroom.data.TravelAddressBMPBean#getStreetName
	 */
	public String getStreetName();

	/**
	 * @see com.idega.block.trade.stockroom.data.TravelAddressBMPBean#getTime
	 */
	public Timestamp getTime();

	/**
	 * @see com.idega.block.trade.stockroom.data.TravelAddressBMPBean#getAddressType
	 */
	public int getAddressType();

	/**
	 * @see com.idega.block.trade.stockroom.data.TravelAddressBMPBean#getRefillStock
	 */
	public boolean getRefillStock();

	/**
	 * @see com.idega.block.trade.stockroom.data.TravelAddressBMPBean#setAddressId
	 */
	public void setAddressId(int addressId);

	/**
	 * @see com.idega.block.trade.stockroom.data.TravelAddressBMPBean#setAddress
	 */
	public void setAddress(Address address);

	/**
	 * @see com.idega.block.trade.stockroom.data.TravelAddressBMPBean#setTime
	 */
	public void setTime(Timestamp stamp);

	/**
	 * @see com.idega.block.trade.stockroom.data.TravelAddressBMPBean#setTime
	 */
	public void setTime(IWTimestamp stamp);

	/**
	 * @see com.idega.block.trade.stockroom.data.TravelAddressBMPBean#setAddressTypeId
	 */
	public void setAddressTypeId(int id);

	/**
	 * @see com.idega.block.trade.stockroom.data.TravelAddressBMPBean#setRefillStock
	 */
	public void setRefillStock(boolean replenish);

	/**
	 * @see com.idega.block.trade.stockroom.data.TravelAddressBMPBean#setGroupName
	 */
	public void setGroupName(String groupName);

	/**
	 * @see com.idega.block.trade.stockroom.data.TravelAddressBMPBean#getGroupName
	 */
	public String getGroupName();

	/**
	 * @see com.idega.block.trade.stockroom.data.TravelAddressBMPBean#setOrder
	 */
	public void setOrder(int order);

	/**
	 * @see com.idega.block.trade.stockroom.data.TravelAddressBMPBean#getOrder
	 */
	public int getOrder();
	
	public Collection getDiscountCodeGroups();

	public void removeDiscountCodeGroup(DiscountCodeGroup discountCodeGroup);

	public void addDiscountCodeGroup(DiscountCodeGroup discountCodeGroup);
}