package com.idega.block.trade.stockroom.data;


import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.data.IDOHome;

public interface PriceCategoryHome extends IDOHome {
	public PriceCategory create() throws CreateException;

	public PriceCategory findByPrimaryKey(Object pk) throws FinderException;

	public PriceCategory findByPrimaryKey(int id) throws javax.ejb.FinderException;

	public PriceCategory findByPrimaryKeyLegacy(int id) throws java.sql.SQLException;

	public Collection findGroupedCategories(PriceCategory cat) throws FinderException;

	public PriceCategory findByKey(String key) throws FinderException;

	public Collection findBySupplierAndCountAsPerson(int supplierID, boolean countAsPerson) throws FinderException;
}