/*
 * $Id: ProductPriceBusinessBean.java,v 1.13 2007/04/23 15:53:14 gimmi Exp $
 * Created on Aug 10, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.trade.stockroom.business;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.ejb.FinderException;
import javax.xml.rpc.ServiceException;

import com.idega.block.trade.data.Currency;
import com.idega.block.trade.data.CurrencyHome;
import com.idega.block.trade.stockroom.data.PriceCategory;
import com.idega.block.trade.stockroom.data.PriceCategoryBMPBean;
import com.idega.block.trade.stockroom.data.PriceCategoryHome;
import com.idega.block.trade.stockroom.data.Product;
import com.idega.block.trade.stockroom.data.ProductHome;
import com.idega.block.trade.stockroom.data.ProductPrice;
import com.idega.block.trade.stockroom.data.ProductPriceHome;
import com.idega.block.trade.stockroom.data.Timeframe;
import com.idega.block.trade.stockroom.data.TravelAddress;
import com.idega.block.trade.stockroom.webservice.client.TradeService_PortType;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.business.IBOServiceBean;
import com.idega.data.IDOLookup;
import com.idega.data.IDOLookupException;
import com.idega.data.IDORelationshipException;
import com.idega.data.IDORuntimeException;
import com.idega.util.IWTimestamp;


public class ProductPriceBusinessBean extends IBOServiceBean  implements ProductPriceBusiness{

	private HashMap mapForProductPriceMap = new HashMap();
	private HashMap mapForMiscellaniousPriceMap = new HashMap();

	public Collection getProductPrices(int productId, int timeframeId, int addressId, int[] visibility, IWTimestamp date) throws FinderException {
		return getProductPrices(productId, timeframeId, addressId, -1, visibility, null, date);
	}

	public Collection getProductPrices(int productId, int timeframeId, int addressId, boolean netbookingOnly, IWTimestamp date) throws FinderException {
		return getProductPrices(productId, timeframeId, addressId, -1, netbookingOnly, null, date);
	}

	public Collection getProductPrices(int productId, int timeframeId, int addressId, boolean netbookingOnly, String key, IWTimestamp date) throws FinderException {
		return getProductPrices(productId, timeframeId, addressId, -1, netbookingOnly, key, date);
	}

	public Collection getProductPrices(int productId, int timeframeId, int addressId, int currencyId, boolean netbookingOnly, String key, IWTimestamp date) throws FinderException {
		int[] vis;
		if (netbookingOnly) {
			vis = new int[] {PriceCategoryBMPBean.PRICE_VISIBILITY_BOTH_PRIVATE_AND_PUBLIC, PriceCategoryBMPBean.PRICE_VISIBILITY_PUBLIC};	
		}else {
			vis = new int[] {PriceCategoryBMPBean.PRICE_VISIBILITY_BOTH_PRIVATE_AND_PUBLIC, PriceCategoryBMPBean.PRICE_VISIBILITY_PRIVATE};//, PriceCategoryBMPBean.PRICE_VISIBILITY_PUBLIC};	
		}
		return getProductPrices(productId, timeframeId, addressId, currencyId, vis, key, date);
	}

	public Collection getProductPrices(int productId, int timeframeId, int addressId, int currencyId, int[] visibility, String key, IWTimestamp date) throws FinderException {

		String visString = "";
		if (visibility != null) {
			for (int i = 0; i < visibility.length; i++) {
				visString += visibility[i];
			}
		}
		boolean lookForDate = false;
		StringBuffer mapKey = new StringBuffer(productId).append("_").append(timeframeId).append("_").append(addressId).append("_").append(currencyId).
		append("_").append(visString).append("_").append(key);
		StringBuffer mapDateKey = mapKey;
		if (date != null) {
			mapDateKey.append("_").append(date.toSQLDateString());
			lookForDate = true;
		}

		HashMap priceMap = getPriceMapForProduct(new Integer(productId));
//		HashMap priceMap = new HashMap();
//		System.out.println("[ProductPriceBusiness] priceMap set to EMPTY");
//		System.out.println("[ProductPriceBusinessBean] mapKey = "+mapKey);

//		Timer t =  new Timer();
//		t.start();

		Collection prices = null;

		// Checking for stored price for this day
		if (date != null) {
			if (priceMap.containsKey(mapDateKey)) {
				prices = (Collection) priceMap.get(mapDateKey);
				lookForDate = false;
			}
		}

		// Checking for stored price in general
		if (prices == null) {
			prices = (Collection) priceMap.get(mapKey);
		}

		prices = null;
		if (prices == null || lookForDate) {
			Collection tmp = null;
			if (prices != null) {
				tmp = prices;
			} else {
				tmp = getProductPriceHome().findProductPrices(productId, timeframeId, addressId, 0, currencyId, visibility, key);
			}

			if (date != null) {
				prices = new Vector();
				Date exactDate = date.getDate();

				Iterator iter = tmp.iterator();
				ProductPrice price;
				while (iter.hasNext()) {
					price = (ProductPrice) iter.next();

					Collection coll = getProductPriceHome().findProductPrices(productId, timeframeId, addressId, currencyId, price.getPriceCategoryID(), exactDate);

					if (coll != null && !coll.isEmpty()) {
						Iterator tmpIter = coll.iterator();
						while (tmpIter.hasNext()) {
							prices.add(tmpIter.next());
						}
					} else {
						prices.add(price);
					}

				}
				// Adding the new "improved" prices to the map
				priceMap.put(mapDateKey, prices);
			} else {
				// Adding the orginal collection to the map
				priceMap.put(mapKey, tmp);
				prices = tmp;
			}

		}

//		t.stop();
//		System.out.println("[ProductPriceBusinessBean] time to get prices = "+t.getTimeString());

		return prices;
	}

	private HashMap getPriceMapForProduct(Object productID) {
		HashMap t = (HashMap) this.mapForProductPriceMap.get(productID);
		if (t == null) {
			t = new HashMap();
			this.mapForProductPriceMap.put(productID, t);
		}

		return t;
	}

	private HashMap getMiscMapForProduct(Object productID) {
		HashMap t = (HashMap) this.mapForMiscellaniousPriceMap.get(productID);
		if (t == null) {
			t = new HashMap();
			mapForMiscellaniousPriceMap.put(productID, t);
		}
		return t;
	}
	
	public boolean invalidateCache(PriceCategory cat) {
		try {
			ProductHome pHome = (ProductHome) IDOLookup.getHome(Product.class);
			Collection coll = pHome.findByPriceCategory(cat);
			if (coll != null) {
				Iterator iter = coll.iterator();
				while (iter.hasNext()) {
					invalidateCache( ((Product) iter.next()).getPrimaryKey().toString() );
				}
			}
			return true;
		} catch (IDOLookupException e) {
			e.printStackTrace();
		} catch (IDORelationshipException e) {
			e.printStackTrace();
		} catch (FinderException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean invalidateCache(String productId) {
		return  invalidateCache(productId, null);
	}

	public Collection getGroupedCategories(ProductPrice price) throws RemoteException {
		PriceCategory cat = price.getPriceCategory();
		Collection cats = getGroupedCategories(cat);
		Vector v = new Vector();

		if (cats != null && !cats.isEmpty()) {
			Iterator iter = cats.iterator();

			int timeframeID = -1;
			int addressID = -1;
			try {
				Collection tFrames = price.getTimeframes();
				if (tFrames != null && !tFrames.isEmpty()) {
					timeframeID = ((Timeframe) tFrames.iterator().next()).getID();
				}
			} catch (IDORelationshipException e) {
				e.printStackTrace();
			}
			try {
				Collection tAddress =price.getTravelAddresses();
				if (tAddress != null && !tAddress.isEmpty()) {
					addressID = ((TravelAddress) tAddress.iterator().next()).getID();
				}
			} catch (IDORelationshipException e) {
				e.printStackTrace();
			}

			while (iter.hasNext()) {
				PriceCategory pCat = (PriceCategory) iter.next();
				try {
					ProductPrice pp = getStockroomBusiness().getProductPrice(-1, price.getProductId(), ((Integer)pCat.getPrimaryKey()).intValue(), price.getCurrencyId(), IWTimestamp.getTimestampRightNow(), timeframeID, addressID);
					getStockroomBusiness().getPrice(pp, IWTimestamp.getTimestampRightNow(), timeframeID, addressID);
					v.add(pCat);
				} catch (ProductPriceException  p) {
//					System.out.println("[ProductPriceBusiness] Did not find price for the connected category");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		if (v != null && v.isEmpty()) {
			v= null;
		}

		return v;
	}
	public Collection getGroupedCategories(PriceCategory category) {
		try {
			Collection coll = getPriceCategoryHome().findGroupedCategories(category);
			if (coll != null && coll.size() <= 1) {
				return null;
			}
			return coll;
		} catch (FinderException e) {
			e.printStackTrace();
		}
		return null;
	}
	public PriceCategoryHome getPriceCategoryHome() {
		try {
			return (PriceCategoryHome) IDOLookup.getHome(PriceCategory.class);
		} catch (IDOLookupException e) {
			throw new IDORuntimeException(e);
		}
	}

	public boolean invalidateCache(String productID, String remoteDomainToExclude) {
		this.mapForProductPriceMap.put(new Integer(productID), null);
		this.mapForMiscellaniousPriceMap.put(new Integer(productID), null);

		System.out.println("[ProductPriceBusiness] invalidateCache for product "+productID);
		try {
			Collection coll = getStockroomBusiness().getService_PortTypes(remoteDomainToExclude);
			Iterator iter = coll.iterator();
			while (iter.hasNext()) {
				((TradeService_PortType) iter.next()).invalidatePriceCache(productID, remoteDomainToExclude);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}

//		getStockroomBusiness().executeRemoteService(remoteDomainToExclude, "invalidatePriceCache&productID="+productID);
		return true;
	}

	public Collection getCurrenciesInUse(Product product) throws IDOLookupException, FinderException {
		int[] currIDs = getProductPriceHome().getCurrenciesInUse(((Integer)product.getPrimaryKey()).intValue());
		CurrencyHome cHome = (CurrencyHome) IDOLookup.getHome(Currency.class);
		Vector v = new Vector();
		if (currIDs != null) {
			for (int i = 0; i < currIDs.length; i++) {
				v.add(cHome.findByPrimaryKey(currIDs[i]));
			}
		}
		return v;
	}

	public Collection getMiscellaneousPrices(int productId, int timeframeId, int addressId, boolean netBookingOnly) throws FinderException {
		return getMiscellaneousPrices(productId, timeframeId, addressId, netBookingOnly, -1);
//		return getProductPriceHome().findMiscellaneousPrices(productId, timeframeId, addressId, netBookingOnly, -1);
	}

	public Collection getMiscellaneousPrices(int productId, int timeframeId, int addressId, boolean netBookingOnly, int currencyId) throws FinderException {
		StringBuffer key = new StringBuffer(productId).append("_").append(timeframeId).append("_").append(addressId)
		.append("_").append(netBookingOnly).append("_").append(currencyId);
		
		Integer pk = new Integer(productId);
		HashMap miscmap = getMiscMapForProduct(pk);
		Collection coll = (Collection) miscmap.get(key.toString());
		if (coll == null) {
			coll = getProductPriceHome().findMiscellaneousPrices(productId, timeframeId, addressId, netBookingOnly, currencyId);
			miscmap.put(key.toString(), coll);
		}
		return coll;
//		return getProductPriceHome().findProductPrices(productId, timeframeId, addressId, netBookingOnly, 1, currencyId, null);
	}

	protected StockroomBusiness getStockroomBusiness() {
		try {
			return (StockroomBusiness) IBOLookup.getServiceInstance(getIWApplicationContext(), StockroomBusiness.class);
		}
		catch (IBOLookupException e) {
			throw new IBORuntimeException(e);
		}
	}

	public ProductPriceHome getProductPriceHome() {
		try {
			return (ProductPriceHome) IDOLookup.getHome(ProductPrice.class);
		}
		catch (IDOLookupException e) {
			throw new IDORuntimeException(e);
		}
	}

}
