package com.idega.block.trade.data;


import com.idega.data.IDOLegacyEntity;

public interface Currency extends IDOLegacyEntity {
	/**
	 * @see com.idega.block.trade.data.CurrencyBMPBean#getName
	 */
	public String getName();

	/**
	 * @see com.idega.block.trade.data.CurrencyBMPBean#getCurrencyName
	 */
	public String getCurrencyName();

	/**
	 * @see com.idega.block.trade.data.CurrencyBMPBean#getCurrencyAbbreviation
	 */
	public String getCurrencyAbbreviation();

	/**
	 * @see com.idega.block.trade.data.CurrencyBMPBean#setCurrencyName
	 */
	public void setCurrencyName(String name);

	/**
	 * @see com.idega.block.trade.data.CurrencyBMPBean#setCurrencyAbbreviation
	 */
	public void setCurrencyAbbreviation(String abbreviation);
}