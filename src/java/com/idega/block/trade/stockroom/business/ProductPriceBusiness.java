package com.idega.block.trade.stockroom.business;


import java.util.Collection;
import com.idega.util.IWTimestamp;
import javax.ejb.FinderException;

import com.idega.block.trade.stockroom.data.PriceCategoryHome;
import com.idega.block.trade.stockroom.data.Product;
import com.idega.block.trade.stockroom.data.ProductPrice;
import com.idega.block.trade.stockroom.data.ProductPriceHome;
import com.idega.block.trade.stockroom.data.PriceCategory;
import com.idega.business.IBOService;
import com.idega.data.IDOLookupException;

import java.rmi.RemoteException;

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
}