package com.idega.block.trade.stockroom.data;


import com.idega.data.IDOLegacyEntity;

public interface PriceCategory extends IDOLegacyEntity {
	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#getValue
	 */
	public float getValue();

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#setValue
	 */
	public void setValue(float value);

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#getGroupedWith
	 */
	public PriceCategory getGroupedWith();

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#setGroupedWith
	 */
	public void setGroupedWith(PriceCategory cat);

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#setGroupedWith
	 */
	public void setGroupedWith(Object catId);

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#getIsBooleanCategory
	 */
	public boolean getIsBooleanCategory();

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#setIsBooleanCategory
	 */
	public void setIsBooleanCategory(boolean isBoolean);

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#getName
	 */
	public String getName();

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#setName
	 */
	public void setName(String name);

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#getDescription
	 */
	public String getDescription();

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#setDescription
	 */
	public void setDescription(String description);

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#getExtraInfo
	 */
	public String getExtraInfo();

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#setExtraInfo
	 */
	public void setExtraInfo(String extraInfo);

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#getType
	 */
	public String getType();

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#setType
	 */
	public void setType(String type);

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#isNetbookingCategory
	 */
	public void isNetbookingCategory(boolean value);

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#isNetbookingCategory
	 */
	public boolean isNetbookingCategory();

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#setSupplierId
	 */
	public void setSupplierId(int id);

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#getSupplierId
	 */
	public int getSupplierId();

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#setParentId
	 */
	public void setParentId(int id);

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#getParentId
	 */
	public int getParentId();

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#setKey
	 */
	public void setKey(String key);

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#getKey
	 */
	public String getKey();

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#setCountAsPerson
	 */
	public void setCountAsPerson(boolean countAsPerson);

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#getCountAsPerson
	 */
	public boolean getCountAsPerson();

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#getVisibility
	 */
	public int getVisibility();

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#setVisibility
	 */
	public void setVisibility(int visibility);

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#getOrderNumber
	 */
	public int getOrderNumber();

	/**
	 * @see com.idega.block.trade.stockroom.data.PriceCategoryBMPBean#setOrderNumber
	 */
	public void setOrderNumber(int orderNr);
}