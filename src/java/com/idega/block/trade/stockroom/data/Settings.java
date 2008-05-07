package com.idega.block.trade.stockroom.data;


import com.idega.data.IDOAddRelationshipException;
import com.idega.data.IDOEntity;

public interface Settings extends IDOEntity {
	/**
	 * @see com.idega.block.trade.stockroom.data.SettingsBMPBean#setSupplier
	 */
	public void setSupplier(Supplier supplier)
			throws IDOAddRelationshipException;

	/**
	 * @see com.idega.block.trade.stockroom.data.SettingsBMPBean#setResellet
	 */
	public void setReseller(Reseller reseller)
			throws IDOAddRelationshipException;

	/**
	 * @see com.idega.block.trade.stockroom.data.SettingsBMPBean#getIfDoubleConfirmation
	 */
	public boolean getIfDoubleConfirmation();

	/**
	 * @see com.idega.block.trade.stockroom.data.SettingsBMPBean#getIfEmailAfterOnlineBooking
	 */
	public boolean getIfEmailAfterOnlineBooking();

	/**
	 * @see com.idega.block.trade.stockroom.data.SettingsBMPBean#getGlobalOnlineDiscount
	 */
	public float getGlobalOnlineDiscount();

	/**
	 * @see com.idega.block.trade.stockroom.data.SettingsBMPBean#getCurrencyId
	 */
	public int getCurrencyId();

	/**
	 * @see com.idega.block.trade.stockroom.data.SettingsBMPBean#setIfDoubleConfirmation
	 */
	public void setIfDoubleConfirmation(boolean doubleConfirmation);

	/**
	 * @see com.idega.block.trade.stockroom.data.SettingsBMPBean#setIfEmailAfterOnlineBooking
	 */
	public void setIfEmailAfterOnlineBooking(boolean emailAfterOnlineBooking);

	/**
	 * @see com.idega.block.trade.stockroom.data.SettingsBMPBean#setCurrencyId
	 */
	public void setCurrencyId(int currencyId);

	/**
	 * @see com.idega.block.trade.stockroom.data.SettingsBMPBean#setGlobalOnlineDiscount
	 */
	public void setGlobalOnlineDiscount(float disc);
}