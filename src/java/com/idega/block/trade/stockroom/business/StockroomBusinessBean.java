package com.idega.block.trade.stockroom.business;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.xml.rpc.ServiceException;

import com.idega.block.trade.business.CurrencyBusiness;
import com.idega.block.trade.business.CurrencyHolder;
import com.idega.block.trade.stockroom.data.PriceCategory;
import com.idega.block.trade.stockroom.data.Product;
import com.idega.block.trade.stockroom.data.ProductPrice;
import com.idega.block.trade.stockroom.data.ProductPriceHome;
import com.idega.block.trade.stockroom.data.Reseller;
import com.idega.block.trade.stockroom.data.ResellerHome;
import com.idega.block.trade.stockroom.data.ResellerStaffGroup;
import com.idega.block.trade.stockroom.data.Settings;
import com.idega.block.trade.stockroom.data.Supplier;
import com.idega.block.trade.stockroom.data.SupplierHome;
import com.idega.block.trade.stockroom.data.SupplierStaffGroupBMPBean;
import com.idega.block.trade.stockroom.data.Timeframe;
import com.idega.block.trade.stockroom.data.TravelAddress;
import com.idega.block.trade.stockroom.webservice.client.TradeServiceServiceLocator;
import com.idega.block.trade.stockroom.webservice.client.TradeService_PortType;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.business.IBOServiceBean;
import com.idega.core.accesscontrol.business.LoginBusinessBean;
import com.idega.core.accesscontrol.business.NotLoggedOnException;
import com.idega.data.EntityControl;
import com.idega.data.EntityFinder;
import com.idega.data.GenericEntity;
import com.idega.data.IDOAddRelationshipException;
import com.idega.data.IDOCompositePrimaryKeyException;
import com.idega.data.IDOFinderException;
import com.idega.data.IDOLookup;
import com.idega.data.IDOLookupException;
import com.idega.idegaweb.IWBundle;
import com.idega.presentation.IWContext;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.IWTimestamp;

/**
 * Title:        IW Trade
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega.is
 * @author 2000 - idega team - <br><a href="mailto:gummi@idega.is">Gudmundur Agust Saemundsson</a><br><a href="mailto:gimmi@idega.is">Grimur Jonsson</a>
 * @version 1.0
 */

public class StockroomBusinessBean extends IBOServiceBean implements StockroomBusiness {

	public static final String REMOTE_TRAVEL_APPLICATION_URL_CSV_LIST = "REMOTE_TRAVEL_APPLICATION_URL_CSV_LIST";
	private static String remoteTravelApplications = null;

  public StockroomBusinessBean() {
  }
  @Override
public void addSupplies(int product_id, float amount) {
    /**@todo: Implement this com.idega.block.trade.stockroom.business.SupplyManager method*/
    throw new java.lang.UnsupportedOperationException("Method addSupplies() not yet implemented.");
  }
  @Override
public void depleteSupplies(int product_id, float amount) {
    /**@todo: Implement this com.idega.block.trade.stockroom.business.SupplyManager method*/
    throw new java.lang.UnsupportedOperationException("Method depleteSupplies() not yet implemented.");
  }
  @Override
public void setSupplyStatus(int product_id, float status) {
    /**@todo: Implement this com.idega.block.trade.stockroom.business.SupplyManager method*/
    throw new java.lang.UnsupportedOperationException("Method addSupplies() not yet implemented.");
  }
  @Override
public float getSupplyStatus(int product_id)  throws SQLException {
    /**@todo: Implement this com.idega.block.trade.stockroom.business.SupplyManager method*/
    throw new java.lang.UnsupportedOperationException("Method getSupplyStatus() not yet implemented.");
  }
  @Override
public float getSupplyStatus(int product_id, Timestamp time) {
    /**@todo: Implement this com.idega.block.trade.stockroom.business.SupplyManager method*/
    throw new java.lang.UnsupportedOperationException("Method getSupplyStatus() not yet implemented.");
  }

  @Override
public ProductPrice setPrice(int productPriceIdToReplace, int productId, int priceCategoryId, int currencyId, Timestamp time, float price, int priceType, int timeframeId, int addressId) throws FinderException, IDOAddRelationshipException, CreateException, RemoteException {
    return setPrice(productPriceIdToReplace, productId, priceCategoryId, currencyId, time, price, priceType, timeframeId, addressId, -1);
  }

  @Override
public ProductPrice setPrice(int productPriceIdToReplace, int productId, int priceCategoryId, int currencyId, Timestamp time, float price, int priceType, int timeframeId, int addressId, int maxUsage) throws FinderException, IDOAddRelationshipException, CreateException, RemoteException {
    if (productPriceIdToReplace != -1) {
        ProductPrice pPrice = ((com.idega.block.trade.stockroom.data.ProductPriceHome)com.idega.data.IDOLookup.getHome(ProductPrice.class)).findByPrimaryKey(new Integer(productPriceIdToReplace));
          pPrice.invalidate();
          pPrice.store();
          getPriceMap(productId).clear();
    }

    return setPrice(productId, priceCategoryId, currencyId, time, price, priceType, timeframeId, addressId, maxUsage, null);
  }

  @Override
public ProductPrice setPrice(int productId, int priceCategoryId, int currencyId, Timestamp time, float price, int priceType, int timeframeId, int addressId) throws IDOAddRelationshipException, CreateException, RemoteException {
    return setPrice(productId, priceCategoryId, currencyId, time, price, priceType, timeframeId, addressId, -1, null);
  }

  @Override
public ProductPrice setPrice(int productId, int priceCategoryId, int currencyId, Timestamp time, float price, int priceType, int timeframeId, int addressId, int maxUsage, Date exactDate) throws IDOAddRelationshipException, CreateException, RemoteException {
	  ProductPrice prPrice = ((com.idega.block.trade.stockroom.data.ProductPriceHome)com.idega.data.IDOLookup.getHome(ProductPrice.class)).create();
	  prPrice.setProductId(productId);
	  prPrice.setCurrencyId(currencyId);
	  prPrice.setPriceCategoryID(priceCategoryId);
	  prPrice.setPriceDate(time);
	  prPrice.setPrice(price);
	  prPrice.setPriceType(priceType);
	  prPrice.setMaxUsage(maxUsage);
	  if(exactDate != null) {
		  prPrice.setExactDate(exactDate);
	  }
	  prPrice.store();
	  if (timeframeId != -1) {
		  prPrice.addTimeframe(new Integer(timeframeId));
	  }
	  if (addressId != -1) {
		  prPrice.addTravelAddress(new Integer(addressId));
	  }
	  getProductPriceBusiness().invalidateCache(Integer.toString(productId));
      getPriceMap(productId).clear();
	  return prPrice;
  }

  @Override
public  float getPrice(int productPriceId, int productId, int priceCategoryId, int currencyId, Timestamp time) throws SQLException, RemoteException {
    return getPrice(productPriceId, productId, priceCategoryId, currencyId, time, -1, -1);
  }

  @Override
public  ProductPrice getPrice(Product product) throws RemoteException {
    StringBuffer buffer = new StringBuffer();
      buffer.append("SELECT * FROM "+com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getProductPriceTableName());
      buffer.append(" WHERE ");
      buffer.append(com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNameProductId() +" = "+product.getID());
      buffer.append(" AND ");
      buffer.append(com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNamePriceCategoryId() +" is null");
      buffer.append(" ORDER BY "+com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNamePriceDate()+" DESC");

    try {
//      EntityFinder.debug = true;
      List prices = EntityFinder.getInstance().findAll(ProductPrice.class, buffer.toString());
//      List prices = EntityFinder.findAll(((com.idega.block.trade.stockroom.data.ProductPriceHome)com.idega.data.IDOLookup.getHomeLegacy(ProductPrice.class)).createLegacy(), buffer.toString());
//      EntityFinder.debug = false;
      if (prices != null) {
				if (prices.size() > 0) {
				  return ((ProductPrice)prices.get(0));
				}
			}
    }catch (IDOFinderException ido) {
      ido.printStackTrace(System.err);
    }

    return null;
  }

  @Override
public float getPrice(int productPriceId, int productId, int priceCategoryId, Timestamp time, int timeframeId, int addressId) throws SQLException, RemoteException {
    return getPrice(productPriceId, productId, priceCategoryId, -1, time, timeframeId, addressId);
  }

  @Override
public float getPrice(int productPriceId, int productId, int priceCategoryId, int currencyId, Timestamp time, int timeframeId, int addressId) throws SQLException, RemoteException  {
	  return getPrice(productPriceId, productId, priceCategoryId, currencyId, time, timeframeId, addressId, null);
  }
  @Override
public float getPrice(int productPriceId, int productId, int priceCategoryId, int currencyId, Timestamp time, int timeframeId, int addressId, Date exactDate) throws SQLException, RemoteException  {
	  ProductPrice price = getProductPrice(productPriceId, productId, priceCategoryId, currencyId, time, timeframeId, addressId, exactDate);
	  return getPrice(price, time, timeframeId, addressId);
  }

  	@Override
	public float getPrice(ProductPrice price, Timestamp time, int timeframeId, int addressId) throws RemoteException, SQLException {
  	  if (price != null) {
		  PriceCategory cat = price.getPriceCategory();
		  if(cat.getType().equals(com.idega.block.trade.stockroom.data.PriceCategoryBMPBean.PRICETYPE_PRICE)){
			  CurrencyHolder iceCurr = CurrencyBusiness.getCurrencyHolder(CurrencyHolder.ICELANDIC_KRONA);
			  if ( iceCurr != null && (price.getCurrencyId() == iceCurr.getCurrencyID())) {
				  return new Float(Math.round( price.getPrice()) ).floatValue();
			  } else {
				  return price.getPrice();
			  }
		  } else if(cat.getType().equals(com.idega.block.trade.stockroom.data.PriceCategoryBMPBean.PRICETYPE_DISCOUNT)){
				  float pr = getPrice(-1, price.getProductId(),cat.getParentId(),price.getCurrencyId(),time, timeframeId, addressId, price.getExactDate());
				  float disc = price.getPrice();
				  return pr*((100-disc) /100);

			  }
		  }
		  return 0;
	  }

  	private HashMap onlineDiscountMap = null;

  	@Override
	public float getOnlineDiscount(ProductPrice price) {
  		if (onlineDiscountMap == null) {
  			onlineDiscountMap = new HashMap();
  		}

  		Float f = (Float) onlineDiscountMap.get(price.getPrimaryKey());
  		if (f == null) {
			try {
				Settings settings = getProductBusiness().getProduct(price.getProductId()).getSupplier().getSettings();
	  			f = new Float(settings.getGlobalOnlineDiscount());
	  			onlineDiscountMap.put(price.getPrimaryKey(), f);
			} catch (IDOAddRelationshipException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (FinderException e) {
				e.printStackTrace();
			} catch (CreateException e) {
				e.printStackTrace();
			}
  		}
  		return f.floatValue();
  	}

  	private HashMap priceMap = null;

  	private HashMap getPriceMap(int productId) {
  		if (priceMap == null) {
  			priceMap = new HashMap();
  		}

  		HashMap pMap = (HashMap) priceMap.get(new Integer(productId));
  		if (pMap == null) {
  			pMap = new HashMap();
  	  		priceMap.put(new Integer(productId), pMap);
  		}

  		return pMap;
  	}

  	@Override
	public void clearCache(int productID) {
  		getPriceMap(productID).clear();
  	}

  	@Override
	public ProductPrice getProductPrice(int productPriceId, int productId, int priceCategoryId, int currencyId, Timestamp time, int timeframeId, int addressId) throws SQLException, RemoteException  {
  		return getProductPrice(productPriceId, productId, priceCategoryId, currencyId, time, timeframeId, addressId, null);
  	}

  	@Override
	public ProductPrice getProductPrice(int productPriceId, int productId, int priceCategoryId, int currencyId, Timestamp time, int timeframeId, int addressId, Date exactDate) throws SQLException, RemoteException  {
  		StringBuffer keyb = new StringBuffer("");
  		keyb.append(productPriceId).append("_").append(priceCategoryId).append("_").append(currencyId)
  		.append("_").append(timeframeId).append("_").append(addressId);
  		if (time != null) {
  			IWTimestamp stamp = new IWTimestamp(time);
  			keyb.append("_").append(stamp.toSQLDateString());
  		}
  		String key = keyb.toString();
//  		getPriceMap(productId).clear();
  		HashMap produPriceMap = getPriceMap(productId);
  		Object returner = produPriceMap.get(key);
  		returner = null;

  		StringBuffer buffer = new StringBuffer();

  		if (returner == null) {
  			try {
  				PriceCategory cat = ((com.idega.block.trade.stockroom.data.PriceCategoryHome)com.idega.data.IDOLookup.getHomeLegacy(PriceCategory.class)).findByPrimaryKeyLegacy(priceCategoryId);
  				ProductPrice ppr = ((ProductPrice)GenericEntity.getStaticInstanceIDO(ProductPrice.class));
  				TravelAddress taddr = ((TravelAddress) GenericEntity.getStaticInstance(TravelAddress.class));
  				Timeframe tfr = ((Timeframe) GenericEntity.getStaticInstance(Timeframe.class));
  				String addrTable = EntityControl.getManyToManyRelationShipTableName(TravelAddress.class, ProductPrice.class);
  				String tfrTable = EntityControl.getManyToManyRelationShipTableName(Timeframe.class, ProductPrice.class);
  				String ppColName = ppr.getEntityDefinition().getPrimaryKeyDefinition().getField().getSQLFieldName();
  				String ppTable = ppr.getEntityDefinition().getSQLTableName();

  				if(cat.getType().equals(com.idega.block.trade.stockroom.data.PriceCategoryBMPBean.PRICETYPE_PRICE)){
  					buffer.append("select p.* from "+ppTable+" p");
  					if (timeframeId != -1) {
  						buffer.append(",  "+tfrTable+" tm");
  					}
  					if (addressId != -1) {
  						buffer.append(", "+addrTable+" am");
  					}
  					buffer.append(" where ");
  					buffer.append("p."+com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNameProductId()+" = "+productId);

  					if (timeframeId != -1) {
  						buffer.append(" and ");
  						buffer.append("tm."+tfr.getIDColumnName()+" = "+timeframeId);
  						buffer.append(" and ");
  						buffer.append("p."+ppColName+" = tm."+ppColName);
  					}
  					if (addressId != -1) {
  						buffer.append(" and ");
  						buffer.append("am."+taddr.getIDColumnName()+" = "+addressId);
  						buffer.append(" and ");
  						buffer.append("p."+ppColName+" = am."+ppColName);
  					}

  					if (productPriceId != -1) {
  						buffer.append(" and ");
  						buffer.append("p."+ppColName+" = "+productPriceId);
  					}
  					buffer.append(" and ");
  					buffer.append("p."+com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNamePriceCategoryId()+" = "+priceCategoryId);
  					buffer.append(" and ");
  					IWTimestamp stamp = new IWTimestamp(time);
//					buffer.append("p."+com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNameCurrencyId()+" = "+currencyId);
//					buffer.append(" and ");
  					if (exactDate == null) {
  						buffer.append("p."+com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNamePriceDate()+" <= '"+stamp.toSQLString(false)+"'");
  						buffer.append(" and p."+com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNameExactDate()+" is null");
  					} else {
  						buffer.append("p."+com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNameExactDate()+" = '"+exactDate.toString()+"'");
  					}
  					buffer.append(" and ");
  					buffer.append("p."+com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNamePriceType()+" = "+com.idega.block.trade.stockroom.data.ProductPriceBMPBean.PRICETYPE_PRICE);
  					buffer.append(" and ");
  					buffer.append("p."+com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNameCurrencyId()+" = "+currencyId);
  					//buffer.append(" and ");
  					//buffer.append("p."+com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNameIsValid()+" = 'Y'");
  					buffer.append(" order by p."+com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNamePriceDate()+ " desc");
//					List result = EntityFinder.findAll(ppr,buffer.toString());
//					List result = EntityFinder.findAll(ppr,buffer.toString());
  					ProductPriceHome ppHome = (ProductPriceHome) IDOLookup.getHome(ProductPrice.class);
  					Collection result = ppHome.findBySQL(buffer.toString());

  					if(result != null && result.size() > 0){
  						Iterator iter = result.iterator();
  						ProductPrice price = (ProductPrice) iter.next();
  						getPriceMap(productId).put(key, price);
  						returner = price;
  					}else{
  						getPriceMap(productId).put(key, "no_price");
  					}
  				}else if(cat.getType().equals(com.idega.block.trade.stockroom.data.PriceCategoryBMPBean.PRICETYPE_DISCOUNT)){
  					buffer.append("select p.* from "+ppTable+" p");
  					if (timeframeId != -1) {
  						buffer.append(",  "+tfrTable+" tm");
  					}
  					if (addressId != -1) {
  						buffer.append(", "+addrTable+" am");
  					}
  					buffer.append(" where ");
  					buffer.append("p."+com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNameProductId()+" = "+productId);

  					if (timeframeId != -1) {
  						buffer.append(" and ");
  						buffer.append("tm."+tfr.getIDColumnName()+" = "+timeframeId);
  						buffer.append(" and ");
  						buffer.append("p."+ppColName+" = tm."+ppColName);
  					}
  					if (addressId != -1) {
  						buffer.append(" and ");
  						buffer.append("am."+taddr.getIDColumnName()+" = "+addressId);
  						buffer.append(" and ");
  						buffer.append("p."+ppColName+" = am."+ppColName);
  					}

  					buffer.append(" and ");
  					buffer.append("p."+com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNamePriceCategoryId()+" = "+priceCategoryId);
  					buffer.append(" and ");

  					if (exactDate == null) {
  						buffer.append("p."+com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNamePriceDate()+" < '"+time.toString()+"'");
  						buffer.append(" and p."+com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNameExactDate()+" is null");
  					} else {
  						buffer.append("p."+com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNameExactDate()+" = '"+exactDate.toString()+"'");
  					}

//  					buffer.append("p."+com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNamePriceDate()+" < '"+time.toString()+"'");
  					buffer.append(" and ");
  					buffer.append("p."+com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNamePriceType()+" = "+com.idega.block.trade.stockroom.data.ProductPriceBMPBean.PRICETYPE_DISCOUNT);
  					buffer.append(" and ");
  					buffer.append("p."+com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNameCurrencyId()+" = "+currencyId);
  					//buffer.append(" and ");
  					//buffer.append("p."+com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNameIsValid()+" = 'Y'");
  					buffer.append(" order by p."+com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNamePriceDate()+ " desc");
  					ProductPriceHome ppHome = (ProductPriceHome) IDOLookup.getHome(ProductPrice.class);
  					Collection result = ppHome.findBySQL(buffer.toString());
//					List result = EntityFinder.findAll(ppr,buffer.toString());
  					if(result != null && result.size() > 0){
  						Iterator iter = result.iterator();
  						ProductPrice price = (ProductPrice) iter.next();
  						returner = price;
  						getPriceMap(productId).put(key, price);
  					}

  				}else{
  					getPriceMap(productId).put(key, "no_price");
  				}
  			}
  			catch (FinderException e) {
  				e.printStackTrace(System.err);
  				returner = null;
  			}
  			catch (IDOCompositePrimaryKeyException e) {
  				e.printStackTrace(System.err);
  				returner = null;
  			}
  		}

  		if (returner != null){
  			if (returner instanceof ProductPrice) {
  				return (ProductPrice) returner;
  			} else if (returner instanceof String) {
  				//System.out.println("[StockroomBusiness] : "+buffer.toString());
				throw new ProductPriceException("No Price Was Found");
  			}
  		} else {
  			//System.out.println("[StockroomBusiness] : "+buffer.toString());
			throw new ProductPriceException("No Price Was Found");
  		}
		return null;

  	}


  /**
   * returns 0.0 if pricecategory is not of type com.idega.block.trade.stockroom.data.PriceCategoryBMPBean.PRICETYPE_DISCOUNT
 * @throws SQLException
 * @throws SQLException
 * @throws FinderException
   */
  @Override
public float getDiscount(int productId, int priceCategoryId, Timestamp time) throws RemoteException, SQLException, FinderException {
    PriceCategory cat = ((com.idega.block.trade.stockroom.data.PriceCategoryHome)com.idega.data.IDOLookup.getHomeLegacy(PriceCategory.class)).findByPrimaryKeyLegacy(priceCategoryId);
    if(cat.getType().equals(com.idega.block.trade.stockroom.data.PriceCategoryBMPBean.PRICETYPE_DISCOUNT)){
      ProductPrice ppr = ((ProductPrice)GenericEntity.getStaticInstance(ProductPrice.class));
		String ppTable = ppr.getEntityDefinition().getSQLTableName();

		StringBuffer buffer = new StringBuffer();
        buffer.append("select * from "+ppTable);
        buffer.append(" where ");
        buffer.append(com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNameProductId()+" = "+productId);
        buffer.append(" and ");
        buffer.append(com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNamePriceCategoryId()+" = "+priceCategoryId);
        buffer.append(" and ");
        buffer.append(com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNamePriceDate()+" < '"+time.toString()+"'");
        buffer.append(" and ");
        buffer.append(com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNameIsValid()+" = 'Y'");
        buffer.append(" order by "+com.idega.block.trade.stockroom.data.ProductPriceBMPBean.getColumnNamePriceDate());
		  ProductPriceHome ppHome = (ProductPriceHome) IDOLookup.getHome(ProductPrice.class);
		  Collection result = ppHome.findBySQL(buffer.toString());
//	      List result = EntityFinder.findAll(ppr,buffer.toString());

      if(result != null && result.size() > 0){
		  Iterator iter = result.iterator();
		  ProductPrice price = (ProductPrice) iter.next();
        return price.getPrice();
      }else{
        return 0;
      }
    }else{
      throw new ProductPriceException();
    }
  }

  @Override
public int createPriceCategory(int supplierId, String name, String description, String extraInfo) throws SQLException {
		return createPriceCategory(supplierId, name, description, extraInfo, null);
  }

  @Override
public int createPriceCategory(int supplierId, String name, String description, String extraInfo, String key)throws SQLException {
  		try {
  			PriceCategory cat = ((com.idega.block.trade.stockroom.data.PriceCategoryHome)com.idega.data.IDOLookup.getHome(PriceCategory.class)).create();
	    cat.setType(com.idega.block.trade.stockroom.data.PriceCategoryBMPBean.PRICETYPE_PRICE);

	    cat.setName(name);

	    if(description != null){
	      cat.setDescription(description);
	    }

	    if(extraInfo != null){
	      cat.setExtraInfo(extraInfo);
	    }

	    if (key != null) {
	    	cat.setKey(key);
	    }

	    cat.insert();

	    return cat.getID();
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
  }


  @Override
public void createPriceDiscountCategory(int parentId, int supplierId, String name, String description, String extraInfo) throws SQLException{
  		try {
	    PriceCategory cat = ((com.idega.block.trade.stockroom.data.PriceCategoryHome)com.idega.data.IDOLookup.getHome(PriceCategory.class)).create();
	    cat.setParentId(parentId);
	    cat.setType(com.idega.block.trade.stockroom.data.PriceCategoryBMPBean.PRICETYPE_DISCOUNT);

	    cat.setName(name);

	    if(description != null){
	      cat.setDescription(description);
	    }

	    if(extraInfo != null){
	      cat.setExtraInfo(extraInfo);
	    }

	    cat.insert();
  		} catch (Exception e) {
  			throw new SQLException(e.getMessage());
  		}

  }


  @Override
public  int getUserSupplierId(User user) throws RuntimeException, SQLException{
  	try {
	  	List gr = user.getParentGroups();
	    if(gr != null){
    		SupplierHome sHome = (SupplierHome) IDOLookup.getHome(Supplier.class);
	      Iterator iter = gr.iterator();
	      while (iter.hasNext()) {
	        Group item = (Group)iter.next();
	        if(item.getGroupType().equals(SupplierStaffGroupBMPBean.GROUP_TYPE_VALUE)){
        		try {
        			Collection coll = sHome.findAllByGroupID( ((Integer) item.getPrimaryKey()).intValue());
        			if (coll != null && !coll.isEmpty()) {
        				return ((Supplier) coll.iterator().next()).getID();
        			}
        		} catch (FinderException fe) {
        			fe.printStackTrace();
        		}

//	          IDOLegacyEntity[] supp = ((Supplier) SupplierBMPBean.getStaticInstance(Supplier.class)).findAllByColumn(SupplierBMPBean.getColumnNameGroupID(),item.getID());
//	          if(supp != null && supp.length > 0){
//	            return supp[0].getID();
//	          }
	        }
	      }
	    }
	    throw new RuntimeException("Does not belong to any supplier");
  	} catch (IDOLookupException e) {
  		e.printStackTrace();
  		throw new RuntimeException("Does not belong to any supplier");
  	}
  }

  @Override
public  int getUserSupplierId(IWContext iwc) throws RuntimeException, SQLException {
    String supplierLoginAttributeString = "sr_supplier_id";

    Object obj = LoginBusinessBean.getLoginAttribute(supplierLoginAttributeString,iwc);
    if(obj != null){
      return ((Integer)obj).intValue();
    }else{
      User us = iwc.getCurrentUser();//LoginBusinessBean.getUser(iwc);
      if(us != null){
        int suppId = getUserSupplierId(us);
        LoginBusinessBean.setLoginAttribute(supplierLoginAttributeString,new Integer(suppId), iwc);
        return suppId;
      } else{
        throw new NotLoggedOnException();
      }
    }
  }

  @Override
public  int getUserResellerId(IWContext iwc) throws RuntimeException, SQLException {
    String resellerLoginAttributeString = "sr_reseller_id";

    Object obj = LoginBusinessBean.getLoginAttribute(resellerLoginAttributeString,iwc);

    if(obj != null){
      return ((Integer)obj).intValue();
    }else{
      User us = iwc.getCurrentUser();//LoginBusinessBean.getUser(iwc);
      if(us != null){
        int resellerId = getUserResellerId(us);
        LoginBusinessBean.setLoginAttribute(resellerLoginAttributeString,new Integer(resellerId), iwc);
        return resellerId;
      } else{
        throw new NotLoggedOnException();
      }
    }
  }


  @Override
public  int getUserResellerId(User user) throws RuntimeException, SQLException{
  	List gr = user.getParentGroups();
		if(gr != null){
			try {
				ResellerHome rHome = (ResellerHome) IDOLookup.getHome(Reseller.class);
				Iterator iter = gr.iterator();
			  while (iter.hasNext()) {
					Group item = (Group)iter.next();
					if(item.getGroupType().equals(((ResellerStaffGroup) GenericEntity.getStaticInstance(ResellerStaffGroup.class)).getGroupTypeValue())){
	      		try {
	      			Collection coll = rHome.findAllByGroupID( item.getPrimaryKey() );
	      			if (coll != null && !coll.isEmpty()) {
	      				return ((Reseller) coll.iterator().next()).getID();
	      			}
	      		} catch (FinderException fe) {
	      			fe.printStackTrace();
	      		}

//	      		IDOLegacyEntity[] reseller = ((Reseller) ResellerBMPBean.getStaticInstance(Reseller.class)).findAllByColumn(ResellerBMPBean.getColumnNameGroupID(),item.getID());
//					  if(reseller != null && reseller.length > 0){
//						return reseller[0].getID();
//					  }
					}
				}
			}
			catch (IDOLookupException e) {
				e.printStackTrace();
			}
		}
		throw new RuntimeException("Does not belong to any reseller");

	/*com.idega.core.data.GenericGroup gGroup = ((com.idega.core.data.GenericGroupHome)com.idega.data.IDOLookup.getHomeLegacy(GenericGroup.class)).createLegacy();
    List gr = gGroup.getAllGroupsContainingUser(user);
    if(gr != null){
      Iterator iter = gr.iterator();
      while (iter.hasNext()) {
        GenericGroup item = (GenericGroup)iter.next();
        if(item.getGroupType().equals(((ResellerStaffGroup)com.idega.block.trade.stockroom.data.ResellerStaffGroupBMPBean.getStaticInstance(ResellerStaffGroup.class)).getGroupTypeValue())){
          IDOLegacyEntity[] reseller = ((Reseller)com.idega.block.trade.stockroom.data.ResellerBMPBean.getStaticInstance(Reseller.class)).findAllByColumn(com.idega.block.trade.stockroom.data.ResellerBMPBean.getColumnNameGroupID(),item.getID());
          if(reseller != null && reseller.length > 0){
            return reseller[0].getID();
          }
        }
      }
    }
    throw new RuntimeException("Does not belong to any reseller");
    */
  }

  @Override
public  int updateProduct(int productId, int supplierId, Integer fileId, String productName, String number, String productDescription, boolean isValid, int discountTypeId) throws Exception{
    return getProductBusiness().createProduct(productId,supplierId, fileId, productName, number, productDescription, isValid, discountTypeId);
  }

  @Override
public  int createProduct(int supplierId, Integer fileId, String productName, String number, String productDescription, boolean isValid, int discountTypeId) throws Exception{
    return getProductBusiness().createProduct(-1,supplierId, fileId, productName, number, productDescription, isValid, discountTypeId);
  }

  private ProductBusiness getProductBusiness() throws RemoteException {
    return IBOLookup.getServiceInstance(getIWApplicationContext(), ProductBusiness.class);
  }

  @Override
public DropdownMenu getCurrencyDropdownMenu(String menuName) {
    DropdownMenu menu = new DropdownMenu(menuName);
    List currencyList = CurrencyBusiness.getCurrencyList();
    Iterator iter = currencyList.iterator();
    while (iter.hasNext()) {
      CurrencyHolder holder = (CurrencyHolder) iter.next();
      menu.addMenuElement(holder.getCurrencyID(), holder.getCurrencyName());
    }

    return menu;
  }

	@Override
	public boolean isInTimeframe(IWTimestamp from, IWTimestamp to, IWTimestamp stampToCheck, boolean yearly) {
		return isBetween(from, to, stampToCheck, yearly, true);
	}

	@Override
	public boolean isBetween(IWTimestamp from, IWTimestamp to, IWTimestamp stampToCheck, boolean yearly, boolean bordersCount) {
		from.setAsDate();
		to.setAsDate();
		if (yearly) {
			IWTimestamp temp = new IWTimestamp(stampToCheck);
			temp.setAsDate();
			if (from.getYear() == to.getYear()) {
				temp.setYear(from.getYear());
				if (bordersCount) {
					return (temp.isLaterThanOrEquals(from) && to.isLaterThanOrEquals(temp));
				}
				else {
					return (temp.isLaterThan(from) && to.isLaterThan(temp));
				}
			}
			else {
				if (temp.getYear() >= to.getYear()) {
					if (temp.getMonth() > to.getMonth()) {
						temp.setYear(from.getYear());
					}
					else {
						temp.setYear(to.getYear());
					}
				}
				return isBetween(from, to, temp, false, bordersCount);
			}
		}
		else {
			if (bordersCount) {
				return (stampToCheck.isLaterThanOrEquals(from) && to.isLaterThanOrEquals(stampToCheck));
			}
			else {
				return (stampToCheck.isLaterThan(from) && to.isLaterThan(stampToCheck));
			}
		}
	}

	protected ProductPriceBusiness getProductPriceBusiness() {
		try {
			return IBOLookup.getServiceInstance(getIWApplicationContext(), ProductPriceBusiness.class);
		}
		catch (IBOLookupException e) {
			throw new IBORuntimeException(e);
		}
	}

	private String getRemoteTravelApplicationUrlCsvList() {
		if (remoteTravelApplications == null) {
			String icABKey = "RemoteTravelAppUrl";
			remoteTravelApplications = getIWApplicationContext().getApplicationSettings().getProperty(icABKey);

			if (remoteTravelApplications == null) {
				IWBundle bundle =  getIWMainApplication().getBundle("com.idega.block.trade");
				String remoteTravelWebs = bundle.getProperty(REMOTE_TRAVEL_APPLICATION_URL_CSV_LIST,"");

				getIWApplicationContext().getApplicationSettings().setProperty(icABKey, remoteTravelWebs, "travel.binding");
				remoteTravelApplications = remoteTravelWebs;
			}
		}

		return remoteTravelApplications;
	}

//	public void executeRemoteService(String remoteDomainToExclude, String methodQuery) {
//		executeRemoteService(remoteDomainToExclude, methodQuery, "/idegaweb/bundles/com.idega.block.trade.bundle/resources/services/IWTradeWS.jws");
//	}

	@Override
	public Collection getService_PortTypes(String remoteDomainToExclude) throws ServiceException, MalformedURLException {
		Collection c = new Vector();
		String remoteTravelWebs = getRemoteTravelApplicationUrlCsvList();
		if(!"".equals(remoteTravelWebs) && remoteTravelWebs != null){

			StringTokenizer tokenizer = new StringTokenizer(remoteTravelWebs,",");
			while(tokenizer.hasMoreTokens()){
				String remoteWeb = tokenizer.nextToken();
				if(remoteDomainToExclude == null || remoteWeb.indexOf(remoteDomainToExclude)==-1){

					if(remoteWeb.endsWith("/")){
						remoteWeb = remoteWeb.substring(0,remoteWeb.length()-1);
					}

					java.rmi.Remote state_port = createServiceStatePortType(remoteWeb);
					c.add(state_port);
				}
				else{
					log("Skipping round-trip decaching for calling remote server : "+remoteDomainToExclude);
				}
			}


		}

		return c;
	}
	protected java.rmi.Remote createServiceStatePortType(String remoteWeb) throws ServiceException, MalformedURLException {
		String endpoint2 = remoteWeb+"/services/TradeService";
		TradeServiceServiceLocator state_locator = new TradeServiceServiceLocator();
		TradeService_PortType state_port = state_locator.getTradeService(new URL(endpoint2));
		return state_port;
	}

//	/**
//	 * <p>
//	 * Method for calling methods on remote domains
//	 * </p>
//	 * @param remoteDomainToExclude
//	 * @param methodQuery
//	 */
//	protected void executeRemoteService(String remoteDomainToExclude, String methodQuery, String webserviceURI) {
//		String remoteTravelWebs = getRemoteTravelApplicationUrlCsvList();
//		if(!"".equals(remoteTravelWebs) && remoteTravelWebs != null){
////			log("Invalidating REMOTE stored search results");
//
//			String prmCallingServer = "remoteCallingHostName";
//			String serverName = null;
//			try {
//				serverName = AxisUtil.getHttpServletRequest().getServerName();
//			} catch (Exception e) {
//				try {
//					serverName = IWContext.getInstance().getRequest().getServerName();
//				} catch (NullPointerException e1) {
//
//				}
//			}
//			StringTokenizer tokenizer = new StringTokenizer(remoteTravelWebs,",");
//			while(tokenizer.hasMoreTokens()){
//				String remoteWeb = tokenizer.nextToken();
//				if(remoteDomainToExclude == null || remoteWeb.indexOf(remoteDomainToExclude)==-1){
//					if(remoteWeb.endsWith("/")){
//						remoteWeb = remoteWeb.substring(0,remoteWeb.length()-1);
//					}
//					String response = FileUtil.getStringFromURL(remoteWeb+webserviceURI+"?method="+methodQuery+"&"+prmCallingServer+"="+serverName);
//					if( response.indexOf("iwtravel-ok")==-1){
//						logError("Webservice method : "+methodQuery+" failed on : "+remoteWeb+" message was : "+response);
//					}
//					else{
//						log("Webservice method : "+methodQuery+" successful for :"+remoteWeb);
//					}
//				}
//				else{
//					log("Skipping round-trip decaching for calling remote server : "+remoteDomainToExclude);
//				}
//			}
//		}
//	}


}
