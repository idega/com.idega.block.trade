package com.idega.block.trade.stockroom.data;


import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import java.sql.SQLException;
import javax.ejb.FinderException;

public interface PriceCategoryHome extends IDOHome {
	public PriceCategory create() throws CreateException;

	public PriceCategory findByPrimaryKey(Object pk) throws FinderException;

	public PriceCategory createLegacy();

	public PriceCategory findByPrimaryKey(int id) throws FinderException;

	public PriceCategory findByPrimaryKeyLegacy(int id) throws SQLException;

	public Collection findGroupedCategories(PriceCategory cat)
			throws FinderException;

	public PriceCategory findByKey(String key) throws FinderException;

	public Collection findBySupplierAndCountAsPerson(int supplierID,
			boolean countAsPerson) throws FinderException;
}