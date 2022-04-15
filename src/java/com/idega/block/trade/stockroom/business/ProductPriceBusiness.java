package com.idega.block.trade.stockroom.business;


import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;

import javax.ejb.FinderException;

import com.idega.block.trade.stockroom.data.PriceCategory;
import com.idega.block.trade.stockroom.data.PriceCategoryHome;
import com.idega.block.trade.stockroom.data.Product;
import com.idega.block.trade.stockroom.data.ProductPrice;
import com.idega.block.trade.stockroom.data.ProductPriceHome;
import com.idega.business.IBOService;
import com.idega.data.IDOLookupException;
import com.idega.util.IWTimestamp;

public interface ProductPriceBusiness extends IBOService {
	/**
	 * @see com.idega.block.trade.stockroom.business.ProductPriceBusinessBean#getProductPrices
	 */
	public Collection getProductPrices(int productId, int timeframeId, int addressId, int[] visibility, IWTimestamp date) throws FinderException, RemoteException;

	public Collection getCurrenciesInUse(Product product) throws IDOLookupException, FinderException;
	/**
	 * @see com.idega.block.trade.stockroom.business.ProductPriceBusinessBean#getProductPrices
	 */
	public Collection getProductPrices(int productId, int timeframeId, int addressId, boolean netbookingOnly, IWTimestamp date) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductPriceBusinessBean#getProductPrices
	 */
	public Collection getProductPrices(int productId, int timeframeId, int addressId, boolean netbookingOnly, String key, IWTimestamp date) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductPriceBusinessBean#getProductPrices
	 */
	public Collection getProductPrices(int productId, int timeframeId, int addressId, int currencyId, boolean netbookingOnly, String key, IWTimestamp date) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductPriceBusinessBean#getProductPrices
	 */
	public Collection getProductPrices(int productId, int timeframeId, int addressId, int currencyId, int[] visibility, String key, IWTimestamp date) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductPriceBusinessBean#invalidateCache
	 */
	public boolean invalidateCache(PriceCategory cat) throws RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductPriceBusinessBean#invalidateCache
	 */
	public boolean invalidateCache(String productId) throws RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductPriceBusinessBean#getGroupedCategories
	 */
	public Collection getGroupedCategories(ProductPrice price) throws RemoteException;
	public Collection getGroupedCategories(PriceCategory category) throws RemoteException;
	public void clearGroupedCategoriesCache();

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductPriceBusinessBean#invalidateCache
	 */
	public boolean invalidateCache(String productID, String remoteDomainToExclude) throws RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductPriceBusinessBean#getMiscellaneousPrices
	 */
	public Collection getMiscellaneousPrices(int productId, int timeframeId, int addressId, boolean netBookingOnly) throws FinderException, RemoteException;
	public Collection getMiscellaneousPrices(int productId, int timeframeId, int addressId, boolean netBookingOnly, IWTimestamp date) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductPriceBusinessBean#getMiscellaneousPrices
	 */
	public Collection getMiscellaneousPrices(int productId, int timeframeId, int addressId, boolean netBookingOnly, int currencyId) throws FinderException, RemoteException;
	public Collection getMiscellaneousPrices(int productId, int timeframeId, int addressId, boolean netBookingOnly, int currencyId, IWTimestamp date) throws FinderException, RemoteException;
	public PriceCategoryHome getPriceCategoryHome();
	/**
	 * @see com.idega.block.trade.stockroom.business.ProductPriceBusinessBean#getProductPriceHome
	 */
	public ProductPriceHome getProductPriceHome() throws RemoteException;
	public float getPrice(int productPriceId, int productId, int priceCategoryId, int currencyId, Timestamp time, int timeframeId, int addressId, Date exactDate) throws SQLException, RemoteException;
  	public float getPrice(ProductPrice price, Timestamp time, int timeframeId, int addressId) throws RemoteException, SQLException;

  	public String formatPrice(float price);

}