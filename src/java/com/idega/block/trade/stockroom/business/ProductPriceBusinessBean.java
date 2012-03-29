/*
 * $Id: ProductPriceBusinessBean.java,v 1.25 2009/06/15 14:07:54 eiki Exp $
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
import java.sql.Timestamp;
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
import com.idega.util.ListUtil;


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
		StringBuffer mapDateKey = new StringBuffer(mapKey.toString());
		if (date != null) {
			mapDateKey.append("_").append(date.toSQLDateString());
			lookForDate = true;
		}

		HashMap priceMap = getPriceMapForProduct(new Integer(productId));
//		HashMap priceMap = new HashMap();
//		System.out.println("[ProductPriceBusiness] priceMap set to EMPTY");
//		System.out.println("[ProductPriceBusinessBean] mapKey = "+mapKey);

//		System.out.println("[ProductPriceBusinessBean] mapKey = "+mapKey.toString());
//		System.out.println("[ProductPriceBusinessBean] mapDateKey = "+mapDateKey.toString());

		Collection prices = null;

		// Checking for stored price for this day
		if (date != null) {
			if (priceMap.containsKey(mapDateKey.toString())) {
				prices = (Collection) priceMap.get(mapDateKey.toString());
				lookForDate = false;
			}
		}

		// Checking for stored price in general
		if (prices == null) {
			prices = (Collection) priceMap.get(mapKey.toString());
		}

		if (ListUtil.isEmpty(prices) || lookForDate) {
			Collection tmp = null;
			if (!ListUtil.isEmpty(prices)) {
				tmp = prices;
			} else {
				tmp = getProductPriceHome().findProductPrices(productId, timeframeId, addressId, 0, currencyId, visibility, key);
			}

			if ( tmp != null && ! tmp.isEmpty() && date != null) {
				prices = new Vector();
				Date exactDate = date.getDate();

				Iterator iter = tmp.iterator();
				ProductPrice price;
				while (iter.hasNext()) {
					price = (ProductPrice) iter.next();

					Collection coll = getProductPriceHome().findProductPrices(productId, timeframeId, addressId, 0, currencyId, price.getPriceCategoryID(), exactDate);

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
				priceMap.put(mapDateKey.toString(), prices);
			} else if (date != null) {
				Date exactDate = date.getDate();
				Collection coll = getProductPriceHome().findProductPrices(productId, timeframeId, addressId, 0, currencyId, -1, exactDate);
				Iterator iter = coll.iterator();
				prices = new Vector();
				while (iter.hasNext()) {
					prices.add(iter.next());
				}
				priceMap.put(mapDateKey.toString(), prices);
			} else {
				// Adding the orginal collection to the map
				priceMap.put(mapKey.toString(), tmp);
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
		
		Object pk = price.getPrimaryKey();
		
		if (priceGroupedCats.containsKey(pk)) {
			return (Collection) priceGroupedCats.get(pk);
		}
		
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
					ProductPrice pp = getStockroomBusiness().getProductPrice(-1, price.getProductId(), ((Integer)pCat.getPrimaryKey()).intValue(), price.getCurrencyId(), IWTimestamp.getTimestampRightNow(), timeframeID, addressID, price.getExactDate());
					getStockroomBusiness().getPrice(pp, IWTimestamp.getTimestampRightNow(), timeframeID, addressID);
					v.add(pCat);
				} catch (ProductPriceException  p) {
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		if (v != null && v.isEmpty()) {
			v= null;
		}

		priceGroupedCats.put(pk, v);
		
		return v;
	}
	
	private HashMap groupedCats = new HashMap();
	private HashMap priceGroupedCats = new HashMap();
		
	public Collection getGroupedCategories(PriceCategory category) {
		Object pk = category.getPrimaryKey();
		if (groupedCats.containsKey(pk)) {
			return (Collection) groupedCats.get(pk);
		} 

		try {
			Collection coll = getPriceCategoryHome().findGroupedCategories(category);
			if (coll != null && coll.size() <= 1) {
				groupedCats.put(pk, null);
				return null;
			}
			groupedCats.put(pk, coll);
			return coll;
		} catch (FinderException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void clearGroupedCategoriesCache() {
		groupedCats.clear();
		priceGroupedCats.clear();
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
//		this.mapForFloatPrices.put(new Integer(productID), null);

//TODO DO IN SEPERATE THREAD!
		System.out.println("[ProductPriceBusiness] invalidateCache for product "+productID);
		try {
			Collection coll = getStockroomBusiness().getService_PortTypes(remoteDomainToExclude);
			Iterator iter = coll.iterator();
			while (iter.hasNext()) {
				((TradeService_PortType) iter.next()).invalidatePriceCache(productID, remoteDomainToExclude);
			}
		} catch (RemoteException e) {
			System.out.println("[ProductPriceBusiness] ===================================");
			System.out.println("[ProductPriceBusiness] WEBSERVICE FAILED (invalidateCache)");
			System.out.println("[ProductPriceBusiness] ===================================");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}

//		getStockroomBusiness().executeRemoteService(remoteDomainToExclude, "invalidatePriceCache&productID="+productID);
		return true;
	}

//	private HashMap mapForFloatPrices = new HashMap();
//
//	private HashMap getFloatPriceMapForProduct(Object productID) {
//		HashMap t = (HashMap) this.mapForFloatPrices.get(productID);
//		if (t == null) {
//			t = new HashMap();
//			this.mapForFloatPrices.put(productID, t);
//		}
//
//		return t;
//	}


	public float getPrice(ProductPrice price, Timestamp time, int timeframeId, int addressId) throws RemoteException, SQLException {
//		if (price != null) {
//			HashMap map = getFloatPriceMapForProduct(new Integer(price.getProductId())); 
//			String key = price.getPrimaryKey()+"_"+timeframeId+"_"+addressId;
//			Float fValue = (Float) map.get(key);
//			if (fValue == null) {
//				float value = getStockroomBusiness().getPrice(price, time, timeframeId, addressId);
//				map.put(key, new Float(value));
//				return value;
//			} else {
//				return fValue.floatValue();
//			}
//		} else {
//			System.out.println("[ProductPriceBUsiness] Wrong usage of getPrice method, need to have productPriceId");
			return getStockroomBusiness().getPrice(price, time, timeframeId, addressId);
//		}
  	}
	public float getPrice(int productPriceId, int productId, int priceCategoryId, int currencyId, Timestamp time, int timeframeId, int addressId, Date exactDate) throws SQLException, RemoteException  {
//		if (productPriceId != -1) {
//			HashMap map = getFloatPriceMapForProduct(new Integer(productId)); 
//			String key = productPriceId+"_"+productId+"_"+currencyId+"_"+timeframeId+"_"+addressId;
//			Float fValue = (Float) map.get(key);
//			if (fValue == null) {
//				float value = getStockroomBusiness().getPrice(productPriceId, productId, priceCategoryId, currencyId, time, timeframeId, addressId);
//				map.put(key, new Float(value));
//				return value;
//			} else {
//				return fValue.floatValue();
//			}
//		} else {
//			System.out.println("[ProductPriceBUsiness] Wrong usage of getPrice method, need to have productPriceId");
			return getStockroomBusiness().getPrice(productPriceId, productId, priceCategoryId, currencyId, time, timeframeId, addressId, exactDate);
//		}
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
		return getMiscellaneousPrices(productId, timeframeId, addressId, netBookingOnly, -1, null);
	}
	public Collection getMiscellaneousPrices(int productId, int timeframeId, int addressId, boolean netBookingOnly, IWTimestamp stamp) throws FinderException {
		return getMiscellaneousPrices(productId, timeframeId, addressId, netBookingOnly, -1, stamp);
//		return getProductPriceHome().findMiscellaneousPrices(productId, timeframeId, addressId, netBookingOnly, -1);
	}

	public Collection getMiscellaneousPrices(int productId, int timeframeId, int addressId, boolean netBookingOnly, int currencyId) throws FinderException {
		return getMiscellaneousPrices(productId, timeframeId, addressId, netBookingOnly, currencyId, null);
	}
	public Collection getMiscellaneousPrices(int productId, int timeframeId, int addressId, boolean netBookingOnly, int currencyId, IWTimestamp date) throws FinderException {

		int[] vis;
		if (netBookingOnly) {
			vis = new int[] {PriceCategoryBMPBean.PRICE_VISIBILITY_BOTH_PRIVATE_AND_PUBLIC, PriceCategoryBMPBean.PRICE_VISIBILITY_PUBLIC};	
		}else {
			vis = new int[] {PriceCategoryBMPBean.PRICE_VISIBILITY_BOTH_PRIVATE_AND_PUBLIC, PriceCategoryBMPBean.PRICE_VISIBILITY_PRIVATE};//, PriceCategoryBMPBean.PRICE_VISIBILITY_PUBLIC};	
		}

		String visString = "";
		if (vis != null) {
			for (int i = 0; i < vis.length; i++) {
				visString += vis[i];
			}
		}
		boolean lookForDate = false;
		StringBuffer mapKey = new StringBuffer(productId).append("_").append(timeframeId).append("_").append(addressId).append("_").append(currencyId).
		append("_").append(visString);
		StringBuffer mapDateKey = new StringBuffer(mapKey.toString());
		if (date != null) {
			mapDateKey.append("_").append(date.toSQLDateString());
			lookForDate = true;
		}

		HashMap miscMap = getMiscMapForProduct(new Integer(productId));
//		HashMap priceMap = new HashMap();
//		System.out.println("[ProductPriceBusiness] priceMap set to EMPTY");
//		System.out.println("[ProductPriceBusinessBean] mapKey = "+mapKey);

//		System.out.println("[ProductPriceBusinessBean] mapKey = "+mapKey.toString());
//		System.out.println("[ProductPriceBusinessBean] mapDateKey = "+mapDateKey.toString());

		Collection prices = null;

		// Checking for stored price for this day
		if (date != null) {
			if (miscMap.containsKey(mapDateKey.toString())) {
				prices = (Collection) miscMap.get(mapDateKey.toString());
				lookForDate = false;
			}
		}

		// Checking for stored price in general
		if (prices == null) {
			prices = (Collection) miscMap.get(mapKey.toString());
		}

		if (prices == null || lookForDate) {
			Collection tmp = null;
			if (prices != null) {
				tmp = prices;
			} else {
				tmp = getProductPriceHome().findProductPrices(productId, timeframeId, addressId, 1, currencyId, vis, null);
			}

			if ( tmp != null && ! tmp.isEmpty() && date != null) {
				prices = new Vector();
				Date exactDate = date.getDate();

				Iterator iter = tmp.iterator();
				ProductPrice price;
				while (iter.hasNext()) {
					price = (ProductPrice) iter.next();

					Collection coll = getProductPriceHome().findProductPrices(productId, timeframeId, addressId, 1, currencyId, price.getPriceCategoryID(), exactDate);

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
				miscMap.put(mapDateKey.toString(), prices);
			} else if (date != null) {
				Date exactDate = date.getDate();
				Collection coll = getProductPriceHome().findProductPrices(productId, timeframeId, addressId, 1, currencyId, -1, exactDate);
				Iterator iter = coll.iterator();
				prices = new Vector();
				while (iter.hasNext()) {
					prices.add(iter.next());
				}
				miscMap.put(mapDateKey.toString(), prices);
			} else {
				// Adding the orginal collection to the map
				miscMap.put(mapKey.toString(), tmp);
				prices = tmp;
			}

		}
		return prices;
		
//		StringBuffer key = new StringBuffer(productId).append("_").append(timeframeId).append("_").append(addressId)
//		.append("_").append(netBookingOnly).append("_").append(currencyId);
//		
//		Integer pk = new Integer(productId);
//		HashMap miscmap = getMiscMapForProduct(pk);
//		Collection coll = (Collection) miscmap.get(key.toString());
//		if (coll == null) {
//			prices = getProductPriceHome().findMiscellaneousPrices(productId, timeframeId, addressId, netBookingOnly, currencyId);
//			miscMap.put(key.toString(), prices);
//		}
//		return prices;
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
