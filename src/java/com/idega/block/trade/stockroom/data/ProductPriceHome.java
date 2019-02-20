package com.idega.block.trade.stockroom.data;


import java.sql.Date;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.data.IDOException;
import com.idega.data.IDOHome;
import com.idega.data.IDOLookupException;
import com.idega.data.IDORelationshipException;

public interface ProductPriceHome extends IDOHome {
	public ProductPrice create() throws CreateException;

	public ProductPrice findByPrimaryKey(Object pk) throws FinderException;

	public void clearPrices(int productId, int currencyId) throws IDOLookupException, FinderException;

	public void clearPrices(int productId, int currencyId, String key) throws FinderException, IDOLookupException;

	public Collection findProductPrices(int productId, boolean netBookingOnly) throws FinderException;

	public Collection findProductPrices(int productId, int timeframeId, int addressId, boolean netBookingOnly, String key) throws FinderException;

	public Collection findProductPrices(int productId, int timeframeId, int addressId, boolean netBookingOnly) throws FinderException;

	public Collection findProductPrices(int productId, int timeframeId, int addressId, int[] visibility, String key) throws FinderException;

	public Collection findMiscellaneousPrices(int productId, int timeframeId, int addressId, boolean netBookingOnly) throws FinderException;

	public Collection findMiscellaneousPrices(int productId, int timeframeId, int addressId, boolean netBookingOnly, int currencyId) throws FinderException;

	public Collection findProductPrices(int productId, int timeframeId, int addressId, boolean netBookingOnly, int countAsPersonStatus, int currencyId, String key) throws FinderException;

	public Collection findProductPrices(int productId, int timeframeId, int addressId, int countAsPersonStatus, int currencyId, int visibility, String key) throws FinderException;

	public Collection findProductPrices(int productId, int timeframeId, int addressId, int countAsPersonStatus, int currencyId, int[] visibility, String key) throws FinderException;

	public Collection findProductPrices(int productId, int timeframeId, int addressId, int countAsPersonStatus, int currencyId, int priceCategoryId, Date exactDate) throws FinderException;

	public int[] getCurrenciesInUse(int productId);

	public int[] getCurrenciesInUse(int productId, int visibility);

	public int[] getCurrenciesInUse(int productId, int[] visibility);

	public boolean hasProductPrices(int productId, int timeframeId, int addressId, boolean netBookingOnly, String key) throws FinderException, IDOException;

	public ProductPrice findByData(int productId, int timeframeId, int addressId, int currencyId, int priceCategoryId, Date date) throws FinderException;

	public Collection findBySQL(String sql) throws FinderException;

	public ProductPrice findIdentical(ProductPrice price, int currencyID) throws FinderException, IDORelationshipException;
	
	public ProductPrice findSideProductPrice(int productId) throws IDORelationshipException, FinderException;
}